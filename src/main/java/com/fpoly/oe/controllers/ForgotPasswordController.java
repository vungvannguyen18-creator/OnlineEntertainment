package com.fpoly.oe.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/user/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String email = req.getParameter("email");
        
        try {
            if (id == null || id.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                req.setAttribute("error", "Vui lòng nhập đầy đủ Tên đăng nhập và Email!");
                req.getRequestDispatcher("/views/user/forgot-password.jsp").forward(req, resp);
                return;
            }

            UserDAO dao = new UserDAO();
            User user = dao.findById(id);
            
            if (user != null && user.getEmail().equals(email)) {
                req.setAttribute("message", "Mật khẩu của bạn là: " + user.getPassword());
            } else {
                req.setAttribute("error", "Tên đăng nhập hoặc Email không đúng!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi, vui lòng thử lại sau.");
        }
        
        req.getRequestDispatcher("/views/user/forgot-password.jsp").forward(req, resp);
    }
}

