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
	                
	                // 4. Gửi Video này sang trang Chi tiết để hiển thị
	                req.setAttribute("video", v);
	                req.getRequestDispatcher("/views/user/detail.jsp").forward(req, resp);
	                return;
	            }
	        }
	        // Nếu không tìm thấy ID thì đá về trang chủ
	        resp.sendRedirect("home");
	    }
	}

