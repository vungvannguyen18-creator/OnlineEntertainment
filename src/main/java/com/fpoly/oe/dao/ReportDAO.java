package com.fpoly.oe.dao;

import java.util.List;
import com.fpoly.oe.entities.Video;

public class ReportDAO {
    
    public List<Object[]> getFavoriteReport() {
        String jpql = "SELECT f.video.title, count(f), max(f.likeDate), min(f.likeDate) "
                    + "FROM Favorite f GROUP BY f.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Object[].class).getResultList();
        }
    }
    
    public List<Video> getVideoHasFavorites() {
        String jpql = "SELECT DISTINCT f.video FROM Favorite f ORDER BY f.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Video.class).getResultList();
        }
    }
    
    public List<Object[]> getFavoriteUsersByVideo(String videoId) {
        String jpql = "SELECT f.user.id, f.user.fullname, f.user.email, f.likeDate "
                    + "FROM Favorite f WHERE f.video.id = :vid";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Object[].class)
                    .setParameter("vid", videoId)
                    .getResultList();
        }
    }
    
    public List<Video> getVideoHasShares() {
        String jpql = "SELECT DISTINCT s.video FROM Share s ORDER BY s.video.title";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Video.class).getResultList();
        }
    }
    
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

