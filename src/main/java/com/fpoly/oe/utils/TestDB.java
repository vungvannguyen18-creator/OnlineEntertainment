package com.fpoly.oe.utils;

import jakarta.persistence.EntityManager;

public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println("Đang kết nối đến Database...");
            EntityManager em = JpaUtils.getEntityManager();
            System.out.println("Kết nối thành công! EntityManager đã được khởi tạo.");
            em.close();
            JpaUtils.getEntityManager().getEntityManagerFactory().close();
        } catch (Exception e) {
            System.out.println("Kết nối thất bại. Lỗi chi tiết:");
            e.printStackTrace();
        }
    }
}

