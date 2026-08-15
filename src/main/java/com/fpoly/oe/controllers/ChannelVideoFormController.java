package com.fpoly.oe.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.beans.VideoFormBean;
import com.fpoly.oe.services.VideoService;

@WebServlet({"/channel/video-form", "/channel/video-form/edit", "/channel/video-form/delete"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
                 maxFileSize = 1024 * 1024 * 10,       // 10MB
                 maxRequestSize = 1024 * 1024 * 50)    // 50MB
public class ChannelVideoFormController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private VideoService videoService;
    
    public ChannelVideoFormController() {
        this.videoService = new VideoService();
    }

    // Danh sách danh mục từ DB
    private List<com.fpoly.oe.entities.Category> getCategoriesFromDB() {
        return new com.fpoly.oe.dao.CategoryDAO().findAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        
        if (uri.contains("/edit")) {
            String id = req.getParameter("id");
            com.fpoly.oe.entities.Video video = new com.fpoly.oe.dao.VideoDAO().findById(id);
            if (video != null) {
                VideoFormBean bean = new VideoFormBean();
                bean.setId(video.getId());
                bean.setTitle(video.getTitle());
                bean.setDescription(video.getDescription());
                bean.setViews(video.getViews());
                bean.setActive(video.isActive());
                if (video.getCategory() != null && video.getCategory().getId() != null) {
                    bean.setCategoryId(String.valueOf(video.getCategory().getId()));
                }
                req.setAttribute("formVideo", bean);
            }
        }
        
        req.setAttribute("categories", getCategoriesFromDB());
        req.getRequestDispatcher("/views/channel/video-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        
        try {
            com.fpoly.oe.entities.User user = (com.fpoly.oe.entities.User) req.getSession().getAttribute("user");
            
            if (uri.contains("/delete")) {
                String videoId = req.getParameter("id");
                videoService.deleteVideo(videoId, user);
                resp.sendRedirect(req.getContextPath() + "/channel/videos");
                return;
            }
            
            req.setAttribute("categories", getCategoriesFromDB());
            VideoFormBean bean = new VideoFormBean();
            
            // Chuyển đổi dữ liệu từ Form qua Bean
            bean.setId(req.getParameter("id"));
            bean.setTitle(req.getParameter("title"));
            bean.setDescription(req.getParameter("description"));
            bean.setCategoryId(req.getParameter("categoryId"));
            bean.setPosterPart(req.getPart("poster"));
            bean.setVideoPart(req.getPart("video"));
            
            String activeStr = req.getParameter("active");
            bean.setActive("true".equals(activeStr));
            
            String viewsStr = req.getParameter("views");
            bean.setViews((viewsStr != null && !viewsStr.isEmpty()) ? Integer.parseInt(viewsStr) : 0);
            
            // Bean kiểm tra lỗi
            boolean isEdit = uri.contains("/edit");
            String error = bean.validate(isEdit);
            if (error != null) {
                req.setAttribute("error", error);
                req.setAttribute("formVideo", bean);
                req.getRequestDispatcher("/views/channel/video-form.jsp").forward(req, resp);
                return;
            }
            
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            
            if (uri.contains("/edit")) {
                videoService.updateVideoFromForm(bean, uploadPath, user);
            } else {
                videoService.createVideoFromForm(bean, uploadPath, user);
            }
            
            resp.sendRedirect(req.getContextPath() + "/channel/videos");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            req.setAttribute("categories", getCategoriesFromDB());
            req.getRequestDispatcher("/views/channel/video-form.jsp").forward(req, resp);
        }
    }
}
