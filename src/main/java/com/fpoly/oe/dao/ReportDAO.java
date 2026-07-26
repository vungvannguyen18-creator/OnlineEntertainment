package com.fpoly.oe.dao;

import java.util.List;
import com.fpoly.oe.entities.Video;

public class ReportDAO {
    
    // 1. Thống kê số lượt thích từng Video (Tab FAVORITES)
    // Trả về List<Object[]>: [0] Title, [1] Count, [2] MaxDate, [3] MinDate
    public List<Object[]> getFavoriteReport() {
        String jpql = "SELECT f.video.title, count(f), max(f.likeDate), min(f.likeDate) "
                    + "FROM Favorite f GROUP BY f.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Object[].class).getResultList();
        }
    }
    
    // 2. Lấy danh sách Video có người thích (Để làm Combobox)
    public List<Video> getVideoHasFavorites() {
        String jpql = "SELECT DISTINCT f.video FROM Favorite f ORDER BY f.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Video.class).getResultList();
        }
    }
    
    // 3. Lấy thông tin người thích theo mã Video (Tab FAVORITE USERS)
    // Trả về List<Object[]>: [0] Username, [1] Fullname, [2] Email, [3] LikeDate
    public List<Object[]> getFavoriteUsersByVideo(String videoId) {
        String jpql = "SELECT f.user.id, f.user.fullname, f.user.email, f.likeDate "
                    + "FROM Favorite f WHERE f.video.id = :vid";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Object[].class)
                    .setParameter("vid", videoId)
                    .getResultList();
        }
    }
    
    // 4. Lấy danh sách Video có người chia sẻ (Để làm Combobox)
    public List<Video> getVideoHasShares() {
        String jpql = "SELECT DISTINCT s.video FROM Share s ORDER BY s.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Video.class).getResultList();
        }
    }
    
    // 5. Lấy thông tin chia sẻ theo mã Video (Tab SHARED FRIENDS)
    // Trả về List<Object[]>: [0] Sender Name, [1] Sender Email, [2] Receiver Email, [3] Sent Date
    public List<Object[]> getSharedFriendsByVideo(String videoId) {
        String jpql = "SELECT s.user.fullname, s.user.email, s.emails, s.sharedDate "
                    + "FROM Share s WHERE s.video.id = :vid";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Object[].class)
                    .setParameter("vid", videoId)
                    .getResultList();
        }
    }
}
