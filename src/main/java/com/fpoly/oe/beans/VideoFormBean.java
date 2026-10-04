package com.fpoly.oe.beans;

import jakarta.servlet.http.Part;
import lombok.Data;

@Data
public class VideoFormBean {
    private String id;
    private String title;
    private Part posterPart;
    private int views;
    private String description;
    private boolean active;
    private String categoryId;
    
    private Part videoPart;

    public VideoFormBean() {
    }

    public VideoFormBean(String id, String title, Part posterPart, int views, String description, boolean active,
            String categoryId, Part videoPart) {
        this.id = id;
        this.title = title;
        this.posterPart = posterPart;
        this.views = views;
        this.description = description;
        this.active = active;
        this.categoryId = categoryId;
        this.videoPart = videoPart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Part getPosterPart() {
        return posterPart;
    }

    public void setPosterPart(Part posterPart) {
        this.posterPart = posterPart;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Part getVideoPart() {
        return videoPart;
    }

    public void setVideoPart(Part videoPart) {
        this.videoPart = videoPart;
    }

    public String validate(boolean isEdit) {
        if (title == null || title.trim().isEmpty()) {
            return "Tiêu đề không được để trống!";
        }
        if (!isEdit && (videoPart == null || videoPart.getSize() == 0)) {
            return "Vui lòng chọn file video!";
        }
        if (!isEdit && (posterPart == null || posterPart.getSize() == 0)) {
            return "Vui lòng chọn ảnh poster!";
        }
        return null; 
    }
}
