package com.fpoly.oe.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import com.fpoly.oe.entities.Follow;
import com.fpoly.oe.entities.Video;
import com.fpoly.oe.utils.JpaUtils;

public class FollowDAO extends AbstractDAO<Follow> {

    public FollowDAO() {
        super(Follow.class);
    }

    public Follow findFollow(String followerId, String channelId) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            String jpql = "SELECT f FROM Follow f WHERE f.follower.id = :followerId AND f.channel.id = :channelId";
            TypedQuery<Follow> query = em.createQuery(jpql, Follow.class);
            query.setParameter("followerId", followerId);
            query.setParameter("channelId", channelId);
            List<Follow> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public boolean isFollowing(String followerId, String channelId) {
        return findFollow(followerId, channelId) != null;
    }

    public List<Video> findVideosFromFollowedChannels(String followerId) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.user.id IN (SELECT f.channel.id FROM Follow f WHERE f.follower.id = :followerId) AND v.active = true ORDER BY v.id DESC";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setParameter("followerId", followerId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long countFollowers(String channelId) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            String jpql = "SELECT count(f) FROM Follow f WHERE f.channel.id = :channelId";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("channelId", channelId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}

