package com.fpoly.oe.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.FollowDAO;
import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.Follow;
import com.fpoly.oe.entities.User;

@WebServlet("/follow")
public class FollowController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String channelId = req.getParameter("channelId");
        if (channelId == null || channelId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        
        FollowDAO followDAO = new FollowDAO();
        UserDAO userDAO = new UserDAO();
        
        User channel = userDAO.findById(channelId);
        if (channel != null && !channel.getId().equals(user.getId())) {
            Follow existingFollow = followDAO.findFollow(user.getId(), channelId);
            if (existingFollow != null) {
                followDAO.delete(existingFollow.getId());
                req.getSession().setAttribute("message", "Đã hủy theo dõi kênh " + channel.getFullname());
            } else {
                Follow follow = new Follow();
                follow.setFollower(user);
                follow.setChannel(channel);
                followDAO.create(follow);
                req.getSession().setAttribute("message", "Đã theo dõi kênh " + channel.getFullname());
            }
        }
        
        String referer = req.getHeader("Referer");
        if (referer != null && !referer.contains("/login") && !referer.contains("/register")) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}

