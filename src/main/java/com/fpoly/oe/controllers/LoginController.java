package com.fpoly.oe.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đọc Cookie để tự động điền form (Remember Me)
        jakarta.servlet.http.Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("saved_id")) {
                    req.setAttribute("saved_id", cookie.getValue());
                }
                if (cookie.getName().equals("saved_pwd")) {
                    req.setAttribute("saved_pwd", cookie.getValue());
                }
            }
        }
        
        req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        
        try {
            UserDAO dao = new UserDAO();
            User user = dao.findById(id);
            
            if (user != null && user.getPassword().equals(password)) {
                // Đăng nhập thành công -> Lưu vào Session
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                
                // Xử lý Ghi nhớ tài khoản (Remember me)
                if (remember != null) {
                    // Nếu tick -> Lưu Cookie 30 ngày
                    jakarta.servlet.http.Cookie cookieId = new jakarta.servlet.http.Cookie("saved_id", id);
                    jakarta.servlet.http.Cookie cookiePwd = new jakarta.servlet.http.Cookie("saved_pwd", password);
                    cookieId.setMaxAge(30 * 24 * 60 * 60);
                    cookiePwd.setMaxAge(30 * 24 * 60 * 60);
                    resp.addCookie(cookieId);
                    resp.addCookie(cookiePwd);
                } else {
                    // Nếu không tick -> Xóa Cookie bằng cách set MaxAge = 0
                    jakarta.servlet.http.Cookie cookieId = new jakarta.servlet.http.Cookie("saved_id", "");
                    jakarta.servlet.http.Cookie cookiePwd = new jakarta.servlet.http.Cookie("saved_pwd", "");
                    cookieId.setMaxAge(0);
                    cookiePwd.setMaxAge(0);
                    resp.addCookie(cookieId);
                    resp.addCookie(cookiePwd);
                }
                
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
                req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống!");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
        }
    }
}
