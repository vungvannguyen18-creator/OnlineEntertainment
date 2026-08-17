package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.FavoriteDAO;
import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Favorite;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/like")
public class LikeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
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
        
        try {
            FavoriteDAO favDao = new FavoriteDAO();
            VideoDAO videoDao = new VideoDAO();
            
            Video video = videoDao.findById(videoId);
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
            
            Favorite existFav = favDao.findByUserIdAndVideoId(user.getId(), videoId);
            
            if (existFav == null) {
                Favorite newFav = new Favorite();
                newFav.setUser(user);
                newFav.setVideo(video);
                newFav.setLikeDate(new Date());
                favDao.create(newFav);
            } else {
                favDao.delete(existFav.getId());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String referer = req.getHeader("Referer");
        if (referer != null && !referer.contains("/login") && !referer.contains("/register")) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/video?id=" + videoId);
        }
    }
}

