package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.ReportDAO;
import com.fpoly.oe.entities.Video;

@WebServlet("/admin/report")
public class AdminReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReportDAO dao = new ReportDAO();
        
        // --- TAB 1: FAVORITES ---
        List<Object[]> favoritesData = dao.getFavoriteReport();
        req.setAttribute("favoritesData", favoritesData);
        
        // --- TAB 2: FAVORITE USERS ---
        List<Video> favVideos = dao.getVideoHasFavorites();
        req.setAttribute("favVideos", favVideos);
        
        String favVid = req.getParameter("favVid");
        if (favVid == null && !favVideos.isEmpty()) {
            favVid = favVideos.get(0).getId(); // Mặc định chọn video đầu tiên
        }
        if (favVid != null) {
            List<Object[]> favUsersData = dao.getFavoriteUsersByVideo(favVid);
            req.setAttribute("favUsersData", favUsersData);
            req.setAttribute("selectedFavVid", favVid);
        }
        
        // --- TAB 3: SHARED FRIENDS ---
        List<Video> shareVideos = dao.getVideoHasShares();
        req.setAttribute("shareVideos", shareVideos);
        
        String shareVid = req.getParameter("shareVid");
        if (shareVid == null && !shareVideos.isEmpty()) {
            shareVid = shareVideos.get(0).getId(); // Mặc định chọn video đầu tiên
        }
        if (shareVid != null) {
            List<Object[]> sharedFriendsData = dao.getSharedFriendsByVideo(shareVid);
            req.setAttribute("sharedFriendsData", sharedFriendsData);
            req.setAttribute("selectedShareVid", shareVid);
        }
        
        // --- XỬ LÝ ACTIVE TAB ---
        String activeTab = req.getParameter("tab");
        if (activeTab == null) activeTab = "favorites";
        req.setAttribute("activeTab", activeTab);
        
        req.getRequestDispatcher("/views/admin/report.jsp").forward(req, resp);
    }
}
