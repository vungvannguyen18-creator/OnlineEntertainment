package com.fpoly.oe.utils;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;
import java.util.List;

public class TestData {
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            List<User> users = dao.findAll();
            System.out.println("Tong so User: " + users.size());
            for (User u : users) {
                System.out.println("ID: " + u.getId() + " - Pass: " + u.getPassword());
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
