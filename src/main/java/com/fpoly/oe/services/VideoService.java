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
        // 1. Xử lý lưu video vào project
        Part videoPart = bean.getVideoPart();
        if (videoPart != null && videoPart.getSize() > 0) {
            String originalFileName = Paths.get(videoPart.getSubmittedFileName()).getFileName().toString();
            String ext = "";
            int i = originalFileName.lastIndexOf('.');
            if (i >= 0) ext = originalFileName.substring(i);
            
            // Tạo ID duy nhất cho video (vì ID giới hạn 50 ký tự, UUID 36 + ext là vừa)
            String newVideoId = java.util.UUID.randomUUID().toString().substring(0, 8) + ext;
            if (ext.isEmpty()) newVideoId += ".mp4"; // Default
            
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            
            String savePath = uploadPath + File.separator + newVideoId;
            videoPart.write(savePath);
            bean.setId(newVideoId);
        }

        // 2. Xử lý lưu ảnh poster vào project
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

        // 3. Chuyển đổi dữ liệu và insert vào DB thông qua DAO
        Video video = new Video();
        video.setId(bean.getId());
        video.setTitle(bean.getTitle());
        video.setDescription(bean.getDescription());
        video.setViews(bean.getViews());
        video.setActive(bean.isActive());
        video.setUser(user); // Set channel owner
        
        if (bean.getCategoryId() != null && !bean.getCategoryId().trim().isEmpty()) {
            com.fpoly.oe.entities.Category cat = new com.fpoly.oe.dao.CategoryDAO().findById(Long.parseLong(bean.getCategoryId()));
            video.setCategory(cat);
        }
        
        // Nếu có upload ảnh thì lưu tên ảnh, nếu không thì dùng chung ID video
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

        // Xử lý upload ảnh poster mới
        Part filePart = bean.getPosterPart();
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String savePath = uploadPath + File.separator + fileName;
            filePart.write(savePath);
            video.setPoster(fileName); // Chỉ cập nhật poster nếu có upload file mới
        }

        // Xử lý upload video mới (ghi đè file cũ cùng ID)
        Part videoPart = bean.getVideoPart();
        if (videoPart != null && videoPart.getSize() > 0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String savePath = uploadPath + File.separator + video.getId();
            videoPart.write(savePath);
        }

        video.setTitle(bean.getTitle());
        video.setDescription(bean.getDescription());
        video.setViews(bean.getViews());
        video.setActive(bean.isActive());
        
        if (bean.getCategoryId() != null && !bean.getCategoryId().trim().isEmpty()) {
            com.fpoly.oe.entities.Category cat = new com.fpoly.oe.dao.CategoryDAO().findById(Long.parseLong(bean.getCategoryId()));
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
