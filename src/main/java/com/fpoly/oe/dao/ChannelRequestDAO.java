package com.fpoly.oe.dao;

import java.util.List;
import com.fpoly.oe.entities.ChannelRequest;
import com.fpoly.oe.utils.JpaUtils;

public class ChannelRequestDAO extends AbstractDAO<ChannelRequest> {
    public ChannelRequestDAO() {
        super(ChannelRequest.class);
    }

    public List<ChannelRequest> findAllPending() {
        try (var session = JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT c FROM ChannelRequest c WHERE c.status = 'PENDING' ORDER BY c.requestDate DESC", ChannelRequest.class)
                    .getResultList();
        }
    }
    
    public ChannelRequest findByUserId(String userId) {
        try (var session = JpaUtils.getEntityManager()) {
            List<ChannelRequest> list = session.createQuery("SELECT c FROM ChannelRequest c WHERE c.user.id = :userId ORDER BY c.requestDate DESC", ChannelRequest.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        }
    }
}
