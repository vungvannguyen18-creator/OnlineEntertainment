package com.fpoly.oe.controllers;

import java.io.IOException;

import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Video;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@WebServlet("/video")
public class VideoController extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	    
	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        String id = req.getParameter("id"); // Lấy ID trên thanh URL
	        
	        if (id != null) {
	            VideoDAO dao = new VideoDAO();
	            Video v = dao.findById(id); // Tìm video trong DB
	            
	            if (v != null) {
	                com.fpoly.oe.entities.User user = (com.fpoly.oe.entities.User) req.getSession().getAttribute("user");
	                
	                // Nếu video không active (nháp), chỉ chủ sở hữu hoặc admin mới được xem
	                if (!v.isActive()) {
	                    if (user == null || (!user.getId().equals(v.getUser().getId()) && !user.isAdmin())) {
	                        resp.sendRedirect("home");
	                        return;
	                    }
	                }
	                
	                // 1. Tăng lượt xem lên 1 và Lưu lại vào Database
	                v.setViews(v.getViews() + 1);
	                dao.update(v);
	                
	                // 2. Ghi nhận tiểu phẩm đã xem bằng Cookie
	                Cookie[] cookies = req.getCookies();
	                String history = "";
	                if (cookies != null) {
	                    for (Cookie c : cookies) {
	                        if (c.getName().equals("viewed_videos")) {
	                            history = c.getValue();
	                            break;
	                        }
	                    }
	                }
	                // Nếu lịch sử chưa có ID này thì thêm vào
	                if (!history.contains(id)) {
	                    history += id + "-";
	                }
	                Cookie historyCookie = new Cookie("viewed_videos", history);
	                historyCookie.setMaxAge(60 * 60 * 24 * 30); // Lưu 30 ngày
	                historyCookie.setPath("/");
	                resp.addCookie(historyCookie);
	                
	                // 3. Lấy danh sách các video đã xem từ Cookie
	                java.util.List<String> viewedIds = new java.util.ArrayList<>();
	                if (!history.isEmpty()) {
	                    String[] ids = history.split("-");
	                    for (String vidId : ids) {
	                        if (!vidId.trim().isEmpty() && !vidId.equals(id)) { // Không hiện lại video đang xem
	                            viewedIds.add(vidId);
	                        }
	                    }
	                }
	                java.util.List<Video> viewedVideos = dao.findVideosByIds(viewedIds);
	                req.setAttribute("viewedVideos", viewedVideos);
	                
	                // Kiểm tra xem User đã đăng ký kênh này chưa
	                if (user != null && v.getUser() != null) {
	                    com.fpoly.oe.dao.FollowDAO followDAO = new com.fpoly.oe.dao.FollowDAO();
	                    boolean isFollowing = followDAO.isFollowing(user.getId(), v.getUser().getId());
	                    req.setAttribute("isFollowing", isFollowing);
	                }
	                
	                // 4. Lấy số lượt thích và số người đăng ký
	                com.fpoly.oe.dao.FavoriteDAO favDAO = new com.fpoly.oe.dao.FavoriteDAO();
	                long likeCount = favDAO.countByVideoId(id);
	                req.setAttribute("likeCount", likeCount);
	                
	                if (v.getUser() != null) {
	                    com.fpoly.oe.dao.FollowDAO followDAO = new com.fpoly.oe.dao.FollowDAO();
	                    long followerCount = followDAO.countFollowers(v.getUser().getId());
	                    req.setAttribute("followerCount", followerCount);
	                }
	                
	                // 5. Lấy danh sách bình luận
	                com.fpoly.oe.dao.CommentDAO commentDAO = new com.fpoly.oe.dao.CommentDAO();
	                java.util.List<com.fpoly.oe.entities.Comment> comments = commentDAO.findByVideoId(id);
	                req.setAttribute("comments", comments);
	                
	                // 6. Gửi Video này sang trang Chi tiết để hiển thị
	                req.setAttribute("video", v);
	                req.getRequestDispatcher("/views/user/detail.jsp").forward(req, resp);
	                return;
	            }
	        }
	        // Nếu không tìm thấy ID thì đá về trang chủ
	        resp.sendRedirect("home");
	    }
	}

