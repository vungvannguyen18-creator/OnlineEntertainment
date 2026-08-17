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
        String searchKeyword = req.getParameter("search");
        
        List<Video> videos;
        long totalVideos;
        
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            videos = dao.findTop6VideosByTitle(searchKeyword.trim(), page);
            totalVideos = dao.countVideosByTitle(searchKeyword.trim());
            req.setAttribute("searchKeyword", searchKeyword.trim());
        } else if (categoryId != null && !categoryId.isEmpty()) {
            try {
                Long catId = Long.parseLong(categoryId);
                videos = dao.findTop6VideosByCategoryAndViews(catId, page);
                totalVideos = dao.countVideosByCategory(catId);
                req.setAttribute("selectedCategory", catId);
            } catch (NumberFormatException e) {
                videos = dao.findTop6VideosByViews(page);
                totalVideos = dao.countAllVideos();
            }
        } else {
            videos = dao.findTop6VideosByViews(page);
            totalVideos = dao.countAllVideos();
        }
        
        int totalPages = (int) Math.ceil((double) totalVideos / 6);
        
        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        
        req.getRequestDispatcher("/views/user/index.jsp").forward(req, resp);
    }
}

