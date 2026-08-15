package com.fpoly.oe.dao;

import com.fpoly.oe.entities.Favorite;

public class FavoriteDAO extends AbstractDAO <Favorite> {
    public FavoriteDAO() {
        super(Favorite.class);
    }
    
    public Favorite findByUserIdAndVideoId(String userId, String videoId) {
        String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Favorite.class)
                    .setParameter("userId", userId)
                    .setParameter("videoId", videoId)
                    .getResultStream().findFirst().orElse(null);
        }
    }
    
    public java.util.List<com.fpoly.oe.entities.Video> findVideosByUserId(String userId) {
        String jpql = "SELECT f.video FROM Favorite f WHERE f.user.id = :userId";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, com.fpoly.oe.entities.Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
    
    public long countByVideoId(String videoId) {
        String jpql = "SELECT count(f) FROM Favorite f WHERE f.video.id = :videoId";
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(jpql, Long.class)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        }
    }
}