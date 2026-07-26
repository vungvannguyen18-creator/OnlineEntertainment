package com.fpoly.oe.services;

import java.io.File;
import java.nio.file.Paths;

import jakarta.servlet.http.Part;
import com.fpoly.oe.beans.VideoFormBean;
import com.fpoly.oe.dao.VideoDAO;
import com.fpoly.oe.entities.Video;

import com.fpoly.oe.entities.User;

public class VideoService {

    private VideoDAO videoDAO;

    public VideoService() {
        this.videoDAO = new VideoDAO();
    }

    public void createVideoFromForm(VideoFormBean bean, String uploadPath, User user) throws Exception {
        // 1. Xử lý lưu ảnh vào project
        Part filePart = bean.getPosterPart();
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String savePath = uploadPath + File.separator + fileName;
            filePart.write(savePath);
        }

        // 2. Chuyển đổi dữ liệu và insert vào DB thông qua DAO
        Video video = new Video();
        video.setId(bean.getId());
        video.setTitle(bean.getTitle());
        video.setDescription(bean.getDescription());
        video.setViews(bean.getViews());
        video.setActive(bean.isActive());
        video.setUser(user); // Set channel owner
        
        if (bean.getCategoryId() != null && !bean.getCategoryId().trim().isEmpty()) {
            com.fpoly.oe.entities.Category cat = new com.fpoly.oe.dao.CategoryDAO().findById(bean.getCategoryId());
            video.setCategory(cat);
        }
        
        // Nếu có upload ảnh thì lưu tên ảnh, nếu không thì lấy ID youtube làm mặc định (hoặc giữ null)
        if (fileName != null) {
            video.setPoster(fileName);
        } else {
            video.setPoster(bean.getId()); 
        }

        videoDAO.create(video);
    }

    public void updateVideoFromForm(VideoFormBean bean, String uploadPath, User user) throws Exception {
        Video video = videoDAO.findById(bean.getId());
        if (video == null || !video.getUser().getId().equals(user.getId())) {
            throw new Exception("Không tìm thấy video hoặc bạn không có quyền sửa!");
        }

        Part filePart = bean.getPosterPart();
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String savePath = uploadPath + File.separator + fileName;
            filePart.write(savePath);
            video.setPoster(fileName); // Chỉ cập nhật poster nếu có upload file mới
        }

        video.setTitle(bean.getTitle());
        video.setDescription(bean.getDescription());
        video.setViews(bean.getViews());
        video.setActive(bean.isActive());
        
        if (bean.getCategoryId() != null && !bean.getCategoryId().trim().isEmpty()) {
            com.fpoly.oe.entities.Category cat = new com.fpoly.oe.dao.CategoryDAO().findById(bean.getCategoryId());
            video.setCategory(cat);
        }

        videoDAO.update(video);
    }
    
    public void deleteVideo(String videoId, User user) throws Exception {
        Video video = videoDAO.findById(videoId);
        if (video == null || !video.getUser().getId().equals(user.getId())) {
            throw new Exception("Không tìm thấy video hoặc bạn không có quyền xóa!");
        }
        videoDAO.delete(videoId);
    }
}
