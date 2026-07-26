package com.fpoly.oe.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            String password = req.getParameter("password");
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            
            UserDAO dao = new UserDAO();
            
            // Check if user exists
            if (dao.findById(id) != null) {
                req.setAttribute("error", "Tên đăng nhập (ID) này đã tồn tại!");
                req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
                return;
            }
            
            // Create new User
            User newUser = new User();
            newUser.setId(id);
            newUser.setPassword(password);
            newUser.setFullname(fullname);
            newUser.setEmail(email);
            newUser.setAdmin(false);
            
            dao.create(newUser);
            
            req.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi đăng ký: Có thể Email đã được sử dụng!");
            req.getRequestDispatcher("/views/user/register.jsp").forward(req, resp);
        }
    }
}
