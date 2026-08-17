package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.CommentDAO;
import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Comment;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String commentIdStr = req.getParameter("id");
            String videoId = req.getParameter("videoId");
            
            if (commentIdStr != null && !commentIdStr.isEmpty()) {
                try {
                    Long commentId = Long.parseLong(commentIdStr);
                    CommentDAO cdao = new CommentDAO();
                    Comment c = cdao.findById(commentId);
                    
                    if (c != null && (c.getUser().getId().equals(user.getId()) || user.isAdmin())) {
                        cdao.deleteWithReplies(commentId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (videoId != null) {
                resp.sendRedirect(req.getContextPath() + "/video?id=" + videoId);
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        req.setCharacterEncoding("UTF-8");
        String videoId = req.getParameter("videoId");
        String content = req.getParameter("content");
        String parentIdStr = req.getParameter("parentId");
        
        if (videoId != null && content != null && !content.trim().isEmpty()) {
            VideoDAO vdao = new VideoDAO();
            Video video = vdao.findById(videoId);
            if (video != null) {
                Comment comment = new Comment();
                comment.setUser(user);
                comment.setVideo(video);
                comment.setContent(content.trim());
                comment.setCommentDate(new Date());
                
                if (parentIdStr != null && !parentIdStr.trim().isEmpty()) {
                    try {
                        comment.setParentId(Long.parseLong(parentIdStr));
                    } catch (NumberFormatException e) {
                    }
                }
                
                CommentDAO cdao = new CommentDAO();
                cdao.create(comment);
            }
        }
        
        resp.sendRedirect(req.getContextPath() + "/video?id=" + videoId);
    }
}

