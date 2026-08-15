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

    // Validate data
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
        return null; // Không có lỗi
    }
}
