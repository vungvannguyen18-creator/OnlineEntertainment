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

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/views/user/change-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        
        if (!currentUser.getPassword().equals(oldPassword)) {
            req.setAttribute("error", "Mật khẩu hiện tại không đúng!");
        } else if (newPassword == null || newPassword.length() < 6) {
            req.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự!");
        } else if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
        } else {
            try {
                UserDAO dao = new UserDAO();
                currentUser.setPassword(newPassword);
                dao.update(currentUser);
                req.setAttribute("message", "Đổi mật khẩu thành công!");
                // Cập nhật lại session
                session.setAttribute("user", currentUser);
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Đã xảy ra lỗi khi cập nhật!");
            }
        }
        
        req.getRequestDispatcher("/views/user/change-password.jsp").forward(req, resp);
    }
}
