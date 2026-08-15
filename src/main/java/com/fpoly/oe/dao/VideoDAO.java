package com.fpoly.oe.dao;

import com.fpoly.oe.entities.Video;

public class VideoDAO extends AbstractDAO<Video> {
    public VideoDAO() {
        super(Video.class);
    }
    
    @Override
    public void delete(Object id) {
        jakarta.persistence.EntityManager em = com.fpoly.oe.utils.JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            // Xóa các dữ liệu liên quan trước (Favorites, Shares)
            em.createQuery("DELETE FROM Favorite f WHERE f.video.id = :videoId")
              .setParameter("videoId", id)
              .executeUpdate();
              
            em.createQuery("DELETE FROM Share s WHERE s.video.id = :videoId")
              .setParameter("videoId", id)
              .executeUpdate();
              
            // Xóa video
            Video entity = em.find(Video.class, id);
            if (entity != null) em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    // Khởi đầu: Hiển thị 6 tiểu phẩm, giảm dần theo số lượt xem (chỉ hiện video Active)
    public java.util.List<Video> findTop6VideosByViews(int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.active = true ORDER BY v.views DESC", Video.class)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }
    
    // Đếm tổng số lượng Video để làm Phân trang
    public long countAllVideos() {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v WHERE v.active = true", Long.class)
                    .getSingleResult();
        }
    }
    
    // Hiển thị 6 tiểu phẩm theo danh mục
    public java.util.List<Video> findTop6VideosByCategoryAndViews(Long categoryId, int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.category.id = :categoryId AND v.active = true ORDER BY v.views DESC", Video.class)
                    .setParameter("categoryId", categoryId)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }
    
    // Đếm tổng số Video theo danh mục
    public long countVideosByCategory(Long categoryId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v WHERE v.category.id = :categoryId AND v.active = true", Long.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        }
    }
    
    // Dùng cho Cookie (Danh sách các ID Video đã xem)
    public java.util.List<Video> findVideosByIds(java.util.List<String> ids) {
        if (ids == null || ids.isEmpty()) return new java.util.ArrayList<>();
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.id IN :ids AND v.active = true", Video.class)
                    .setParameter("ids", ids)
                    .getResultList();
        }
    }
    
    // Tìm các video thuộc về kênh của một user (Hiện tất cả kể cả Nháp để trong Kênh có thể xem và sửa)
    public java.util.List<Video> findVideosByUser(String userId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.user.id = :userId ORDER BY v.id DESC", Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
    
    // Tìm kiếm video theo tiêu đề (giống YouTube search)
    public java.util.List<Video> findTop6VideosByTitle(String keyword, int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.title LIKE :keyword AND v.active = true ORDER BY v.views DESC", Video.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }
    
    // Đếm tổng số video khi tìm kiếm theo tiêu đề
    public long countVideosByTitle(String keyword) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v WHERE v.title LIKE :keyword AND v.active = true", Long.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getSingleResult();
        }
    }
}