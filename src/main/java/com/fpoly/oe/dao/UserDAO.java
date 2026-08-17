package com.fpoly.oe.dao;

import com.fpoly.oe.entities.User;

public class UserDAO  extends AbstractDAO<User> {
    public UserDAO() {
        super(User.class);
    }

    @Override
    public java.util.List<User> findAll(int page, int pageSize) {
        try (var session = com.fpoly.oe.utils.JpaUtils.getEntityManager()) {
            return session.createQuery("SELECT u FROM User u ORDER BY u.admin DESC, u.id ASC", User.class)
                    .setFirstResult(page * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }
}
