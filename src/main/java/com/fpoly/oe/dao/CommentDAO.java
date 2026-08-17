package com.fpoly.oe.dao;

import java.util.List;
import com.fpoly.oe.entities.Comment;
import com.fpoly.oe.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class CommentDAO extends AbstractDAO<Comment> {
    public CommentDAO() {
        super(Comment.class);
    }
    
    public List<Comment> findByVideoId(String videoId) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            String jpql = "SELECT c FROM Comment c WHERE c.video.id = :videoId ORDER BY c.commentDate DESC";
            TypedQuery<Comment> query = em.createQuery(jpql, Comment.class);
            query.setParameter("videoId", videoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void deleteWithReplies(Long commentId) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Comment c WHERE c.parentId = :commentId");
            query.setParameter("commentId", commentId);
            query.executeUpdate();
            
            Comment c = em.find(Comment.class, commentId);
            if (c != null) {
                em.remove(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}

