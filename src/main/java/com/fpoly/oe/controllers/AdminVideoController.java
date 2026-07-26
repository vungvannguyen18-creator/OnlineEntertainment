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
        
        // Lấy danh sách video (Không phân trang tạm thời hoặc phân trang 10 video)
        // Mình sẽ lấy hết cho nhanh, trên JSP phân trang sau, hoặc dùng DAO
        List<Video> videos = dao.findAll();
        
        req.setAttribute("formVideo", formVideo);
        req.setAttribute("videos", videos);
        req.setAttribute("activeTab", activeTab);
        
        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        VideoDAO dao = new VideoDAO();
        
        try {
            Video video = new Video();
            video.setId(req.getParameter("id"));
            video.setTitle(req.getParameter("title"));
            video.setViews(Integer.parseInt(req.getParameter("views")));
            video.setDescription(req.getParameter("description"));
            // Active checkbox (Nếu tick thì có giá trị "true", nếu không tick thì null)
            String activeStr = req.getParameter("active");
            video.setActive(activeStr != null);
            
            // Poster có thể bỏ qua hoặc mặc định vì mình dùng ID Youtube
            video.setPoster("yt_poster");
            
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
            } else if (uri.contains("/approve") || uri.contains("/reject")) {
                Video targetVideo = dao.findById(video.getId());
                if (targetVideo != null) {
                    targetVideo.setActive(uri.contains("/approve"));
                    dao.update(targetVideo);
                    req.setAttribute("message", uri.contains("/approve") ? "Đã duyệt video!" : "Đã từ chối video!");
                }
                video = new Video();
                req.setAttribute("activeTab", "videoList");
            }
            
            req.setAttribute("formVideo", video);
            req.setAttribute("activeTab", "videoEdition");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi thao tác dữ liệu: " + e.getMessage());
            req.setAttribute("activeTab", "videoEdition");
        }
        
        // Tải lại danh sách
        List<Video> videos = dao.findAll();
        req.setAttribute("videos", videos);
        req.getRequestDispatcher("/views/admin/video.jsp").forward(req, resp);
    }
}
