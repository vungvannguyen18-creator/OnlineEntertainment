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
            Video entity = em.find(Video.class, id);
            if (entity != null)
                em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public java.util.List<Video> findTop6VideosByViews(int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.active = true ORDER BY v.views DESC", Video.class)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }

    public long countAllVideos() {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT count(v) FROM Video v WHERE v.active = true", Long.class)
                    .getSingleResult();
        }
    }

    public java.util.List<Video> findTop6VideosByCategoryAndViews(Long categoryId, int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(
                    "SELECT v FROM Video v WHERE v.category.id = :categoryId AND v.active = true ORDER BY v.views DESC",
                    Video.class)
                    .setParameter("categoryId", categoryId)
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }

    public long countVideosByCategory(Long categoryId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session
                    .createQuery("SELECT count(v) FROM Video v WHERE v.category.id = :categoryId AND v.active = true",
                            Long.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
        }
    }

    public java.util.List<Video> findVideosByIds(java.util.List<String> ids) {
        if (ids == null || ids.isEmpty())
            return new java.util.ArrayList<>();
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT v FROM Video v WHERE v.id IN :ids AND v.active = true", Video.class)
                    .setParameter("ids", ids)
                    .getResultList();
        }
    }

    public java.util.List<Video> findVideosByUser(String userId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session
                    .createQuery("SELECT v FROM Video v WHERE v.user.id = :userId ORDER BY v.id DESC", Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public java.util.List<Video> findActiveVideosByUser(String userId) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session
                    .createQuery(
                            "SELECT v FROM Video v WHERE v.user.id = :userId AND v.active = true ORDER BY v.id DESC",
                            Video.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }

    public java.util.List<Video> findTop6VideosByTitle(String keyword, int pageNumber) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery(
                    "SELECT v FROM Video v WHERE v.title LIKE :keyword AND v.active = true ORDER BY v.views DESC",
                    Video.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .setFirstResult(pageNumber * 6)
                    .setMaxResults(6)
                    .getResultList();
        }
    }

    public long countVideosByTitle(String keyword) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session
                    .createQuery("SELECT count(v) FROM Video v WHERE v.title LIKE :keyword AND v.active = true",
                            Long.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getSingleResult();
        }
    }
}
