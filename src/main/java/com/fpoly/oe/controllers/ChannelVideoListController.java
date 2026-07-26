package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.User;
import com.fpoly.oe.entities.Video;

@WebServlet("/channel/videos")
public class ChannelVideoListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        // Filter đã lo việc check đăng nhập, nhưng ta cứ check lại cho chắc chắn
        if (user == null || user.getId() == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        VideoDAO videoDAO = new VideoDAO();
        // Lấy danh sách video của chính User đó
        List<Video> videos = videoDAO.findVideosByUser(user.getId());
        
        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/views/channel/video-list.jsp").forward(req, resp);
    }
}
