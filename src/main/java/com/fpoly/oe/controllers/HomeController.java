package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Video;

@WebServlet({"/home", ""})
public class HomeController  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 0;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 0;
            }
        }
        
        VideoDAO dao = new VideoDAO();
        String categoryId = req.getParameter("category");
        
        List<Video> videos;
        long totalVideos;
        
        if (categoryId != null && !categoryId.isEmpty()) {
            videos = dao.findTop6VideosByCategoryAndViews(categoryId, page);
            totalVideos = dao.countVideosByCategory(categoryId);
            req.setAttribute("selectedCategory", categoryId);
        } else {
            videos = dao.findTop6VideosByViews(page);
            totalVideos = dao.countAllVideos();
        }
        
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalVideos / 6);
        
        // Đẩy dữ liệu sang JSP
        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        
        // Chuyển hướng tới trang giao diện
        req.getRequestDispatcher("/views/user/index.jsp").forward(req, resp);
    }
}
