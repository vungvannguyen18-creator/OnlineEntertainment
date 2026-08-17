package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.dao.FollowDAO;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/channel")
public class PublicChannelController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User channelUser = userDAO.findById(id);
        
        if (channelUser == null || !channelUser.getActive()) {
            req.setAttribute("error", "Kênh không tồn tại hoặc đã bị khóa.");
            req.getRequestDispatcher("/views/user/index.jsp").forward(req, resp);
            return;
        }

        VideoDAO videoDAO = new VideoDAO();
        List<Video> videos = videoDAO.findActiveVideosByUser(id);
        
        FollowDAO followDAO = new FollowDAO();
        long followerCount = followDAO.countFollowers(id);
        
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        boolean isFollowing = false;
        
        if (sessionUser != null) {
            isFollowing = followDAO.isFollowing(sessionUser.getId(), id);
        }

        req.setAttribute("channelUser", channelUser);
        req.setAttribute("videos", videos);
        req.setAttribute("followerCount", followerCount);
        req.setAttribute("isFollowing", isFollowing);
        
        req.getRequestDispatcher("/views/user/channel.jsp").forward(req, resp);
    }
}
