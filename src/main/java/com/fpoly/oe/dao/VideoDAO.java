package com.fpoly.oe.dao;

import com.fpoly.oe.entities.Video;

public class VideoDAO extends AbstractDAO<Video> {
    public VideoDAO() {
        super(Video.class);
    }
    
    // Khởi đầu: Hiển thị 6 tiểu phẩm, giảm dần theo số lượt xem
    public java.util.List<Video> findTop6VideosByViews(int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v ORDER BY v.views DESC", Video.class)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }
    
    // Đếm tổng số lượng Video để làm Phân trang
    public long countAllVideos() {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v", Long.class)
                    .getSingleResult();
        }
    }
    
    // Hiển thị 6 tiểu phẩm theo danh mục
    public java.util.List<Video> findTop6VideosByCategoryAndViews(String categoryId, int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.category.id = :categoryId ORDER BY v.views DESC", Video.class)
                    .setParameter("categoryId", categoryId)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }
    
    // Đếm tổng số Video theo danh mục
    public long countVideosByCategory(String categoryId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v WHERE v.category.id = :categoryId", Long.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        }
    }
    
    // Dùng cho Cookie (Danh sách các ID Video đã xem)
    public java.util.List<Video> findVideosByIds(java.util.List<String> ids) {
        if (ids == null || ids.isEmpty()) return new java.util.ArrayList<>();
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.id IN :ids", Video.class)
                    .setParameter("ids", ids)
                    .getResultList();
        }
    }
    
    // Tìm các video thuộc về kênh của một user
    public java.util.List<Video> findVideosByUser(String userId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.user.id = :userId ORDER BY v.id DESC", Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}