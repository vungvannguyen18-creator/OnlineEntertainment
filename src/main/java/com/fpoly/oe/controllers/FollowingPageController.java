package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.FollowDAO;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/following")
public class FollowingPageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.getSession().setAttribute("error", "Vui lòng đăng nhập để xem các kênh đăng ký!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        FollowDAO followDAO = new FollowDAO();
        List<Video> videos = followDAO.findVideosFromFollowedChannels(user.getId());
        
        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/views/user/following.jsp").forward(req, resp);
    }
}

