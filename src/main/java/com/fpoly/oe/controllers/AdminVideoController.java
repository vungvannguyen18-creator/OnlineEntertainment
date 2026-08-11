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

@WebServlet({"/admin/video", "/admin/video/create", "/admin/video/update", "/admin/video/delete", "/admin/video/edit", "/admin/video/approve", "/admin/video/reject"})
public class AdminVideoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO dao = new VideoDAO();
        Video formVideo = new Video();
        
        // Mặc định là Tab Edit hay List? 
        // Đề bài: "Khởi đầu: Hiển thị form trống, hiển thị 10 tiểu phẩm, vô hiệu hóa Update và Delete"
        // Ở đây mình truyền biến 'activeTab' để JSP biết mở tab nào
        String activeTab = "videoList"; 
        
        if (uri.contains("/edit")) {
            String id = req.getParameter("id");
            if (id != null) {
                formVideo = dao.findById(id);
                activeTab = "videoEdition"; // Mở tab Edition
            }
        }
        
        // Lấy danh sách video có phân trang
        loadPagination(req, dao);
        
        req.setAttribute("formVideo", formVideo);
        req.setAttribute("activeTab", activeTab);
        
        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO dao = new VideoDAO();
        
        try {
            Video video = new Video();
            
            if (uri.contains("/approve") || uri.contains("/reject")) {
                String videoId = req.getParameter("id");
                Video targetVideo = dao.findById(videoId);
                if (targetVideo != null) {
                    targetVideo.setActive(uri.contains("/approve"));
                    dao.update(targetVideo);
                    req.setAttribute("message", uri.contains("/approve") ? "Đã duyệt video!" : "Đã từ chối video!");
                }
                req.setAttribute("activeTab", "videoList");
            } else {
                video.setId(req.getParameter("id"));
                video.setTitle(req.getParameter("title"));
                
                String viewsStr = req.getParameter("views");
                if (viewsStr != null && !viewsStr.isEmpty()) {
                    video.setViews(Integer.parseInt(viewsStr));
                } else {
                    video.setViews(0);
                }
                
                video.setDescription(req.getParameter("description"));
                String activeStr = req.getParameter("active");
                video.setActive(activeStr != null);
                video.setPoster("yt_poster");
                
                if (!uri.contains("/delete")) {
                    if (video.getId() == null || video.getId().trim().isEmpty() ||
                        video.getTitle() == null || video.getTitle().trim().isEmpty()) {
                        req.setAttribute("error", "Vui lòng nhập đầy đủ Mã YouTube và Tựa đề!");
                        req.setAttribute("activeTab", "videoEdition");
                        req.setAttribute("formVideo", video);
                        loadPagination(req, dao);
                        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
                        return;
                    }
                }
                
                if (uri.contains("/create")) {
                    dao.create(video);
                    req.setAttribute("message", "Thêm video thành công!");
                } else if (uri.contains("/update")) {
                    dao.update(video);
                    req.setAttribute("message", "Cập nhật video thành công!");
                } else if (uri.contains("/delete")) {
                    dao.delete(video.getId());
                    req.setAttribute("message", "Xóa video thành công!");
                    video = new Video(); // Xóa xong thì form trống
                }
            }
            
            req.setAttribute("formVideo", video);
            req.setAttribute("activeTab", "videoEdition");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi thao tác dữ liệu: " + e.getMessage());
            req.setAttribute("activeTab", "videoEdition");
        }
        
        // Tải lại danh sách
        loadPagination(req, dao);
        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }
    
    private void loadPagination(HttpServletRequest req, VideoDAO dao) {
        int page = 0;
        String pageStr = req.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (Exception e) {}
        }
        int pageSize = 10; // "Khởi đầu: hiển thị 10 tiểu phẩm"
        long totalVideos = dao.countAllVideos();
        int totalPages = (int) Math.ceil((double) totalVideos / pageSize);
        if (page < 0) page = 0;
        if (page >= totalPages && totalPages > 0) page = totalPages - 1;
        
        List<Video> videos = dao.findAll(page, pageSize);
        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
    }
}
