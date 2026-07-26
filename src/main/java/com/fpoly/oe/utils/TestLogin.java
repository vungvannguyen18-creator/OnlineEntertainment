package com.fpoly.oe.utils;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;

public class TestLogin {
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            User user = dao.findById("admin");
            System.out.println("User found: " + (user != null ? user.getFullname() : "null"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
