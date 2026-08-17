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
	        String id = req.getParameter("id"); 
	        
	        if (id != null) {
	            VideoDAO dao = new VideoDAO();
	            Video v = dao.findById(id); 
	            
	            if (v != null) {
	                com.fpoly.oe.entities.User user = (com.fpoly.oe.entities.User) req.getSession().getAttribute("user");
	                
	                if (!v.isActive()) {
	                    if (user == null || (!user.getId().equals(v.getUser().getId()) && !user.isAdmin())) {
	                        resp.sendRedirect("home");
	                        return;
	                    }
	                }
	                
	                v.setViews(v.getViews() + 1);
	                dao.update(v);
	                
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
	                if (!history.contains(id)) {
	                    history += id + "-";
	                }
	                Cookie historyCookie = new Cookie("viewed_videos", history);
	                historyCookie.setMaxAge(60 * 60 * 24 * 30); 
	                historyCookie.setPath("/");
	                resp.addCookie(historyCookie);
	                
	                java.util.List<String> viewedIds = new java.util.ArrayList<>();
	                if (!history.isEmpty()) {
	                    String[] ids = history.split("-");
	                    for (String vidId : ids) {
	                        if (!vidId.trim().isEmpty() && !vidId.equals(id)) { 
	                            viewedIds.add(vidId);
	                        }
	                    }
	                }
	                java.util.List<Video> viewedVideos = dao.findVideosByIds(viewedIds);
	                req.setAttribute("viewedVideos", viewedVideos);
	                
	                if (user != null && v.getUser() != null) {
	                    com.fpoly.oe.dao.FollowDAO followDAO = new com.fpoly.oe.dao.FollowDAO();
	                    boolean isFollowing = followDAO.isFollowing(user.getId(), v.getUser().getId());
	                    req.setAttribute("isFollowing", isFollowing);
	                }
	                
	                com.fpoly.oe.dao.FavoriteDAO favDAO = new com.fpoly.oe.dao.FavoriteDAO();
	                long likeCount = favDAO.countByVideoId(id);
	                req.setAttribute("likeCount", likeCount);
	                
	                if (v.getUser() != null) {
	                    com.fpoly.oe.dao.FollowDAO followDAO = new com.fpoly.oe.dao.FollowDAO();
	                    long followerCount = followDAO.countFollowers(v.getUser().getId());
	                    req.setAttribute("followerCount", followerCount);
	                }
	                
	                com.fpoly.oe.dao.CommentDAO commentDAO = new com.fpoly.oe.dao.CommentDAO();
	                java.util.List<com.fpoly.oe.entities.Comment> comments = commentDAO.findByVideoId(id);
	                req.setAttribute("comments", comments);
	                
	                req.setAttribute("video", v);
	                req.getRequestDispatcher("/views/user/detail.jsp").forward(req, resp);
	                return;
	            }
	        }
	        resp.sendRedirect("home");
	    }
	}


