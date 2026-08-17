package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.ShareDAO;
import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Share;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/share")
public class ShareController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            String uri = req.getRequestURI() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
            session.setAttribute("securityUri", uri);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String videoId = req.getParameter("id");
        if (videoId == null || videoId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        
        VideoDAO videoDao = new VideoDAO();
        Video video = videoDao.findById(videoId);
        
        if (video == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        
        req.setAttribute("video", video);
        req.getRequestDispatcher("/views/user/share.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            String uri = req.getRequestURI();
            session.setAttribute("securityUri", uri);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String videoId = req.getParameter("videoId");
        String email = req.getParameter("email");
        
        try {
            if (email == null || email.trim().isEmpty()) {
                VideoDAO videoDao = new VideoDAO();
                Video video = videoDao.findById(videoId);
                req.setAttribute("video", video);
                req.setAttribute("error", "Vui lòng nhập địa chỉ email!");
                req.getRequestDispatcher("/views/user/share.jsp").forward(req, resp);
                return;
            }

            VideoDAO videoDao = new VideoDAO();
            Video video = videoDao.findById(videoId);
            
            if (video != null) {
                ShareDAO shareDao = new ShareDAO();
                Share share = new Share();
                share.setUser(user);
                share.setVideo(video);
                share.setEmails(email);
                share.setSharedDate(new Date());
                
                shareDao.create(share);
                
                req.setAttribute("message", "Chia sẻ thành công tới email " + email);
            }
            req.setAttribute("video", video);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi chia sẻ video!");
        }
        
        req.getRequestDispatcher("/views/user/share.jsp").forward(req, resp);
    }
}


