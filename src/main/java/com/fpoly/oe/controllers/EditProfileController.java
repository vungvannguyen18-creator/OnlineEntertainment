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

@WebServlet("/edit-profile")
public class EditProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        com.fpoly.oe.entities.User user = (com.fpoly.oe.entities.User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        com.fpoly.oe.dao.ChannelRequestDAO channelDAO = new com.fpoly.oe.dao.ChannelRequestDAO();
        com.fpoly.oe.entities.ChannelRequest channelReq = channelDAO.findByUserId(user.getId());
        req.setAttribute("channelRequest", channelReq);
        
        req.getRequestDispatcher("/views/user/edit-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        
        try {
            if (fullname == null || fullname.trim().isEmpty() || email == null || email.trim().isEmpty()) {
                req.setAttribute("error", "Vui lòng nhập đầy đủ Họ Tên và Email!");
                
                com.fpoly.oe.dao.ChannelRequestDAO channelDAO = new com.fpoly.oe.dao.ChannelRequestDAO();
                com.fpoly.oe.entities.ChannelRequest channelReq = channelDAO.findByUserId(currentUser.getId());
                req.setAttribute("channelRequest", channelReq);
                
                req.getRequestDispatcher("/views/user/edit-profile.jsp").forward(req, resp);
                return;
            }

            UserDAO dao = new UserDAO();
            currentUser.setFullname(fullname);
            currentUser.setEmail(email);
            
            dao.update(currentUser);
            
            req.setAttribute("message", "Cập nhật thông tin thành công!");
            session.setAttribute("user", currentUser);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: Có thể Email này đã được sử dụng bởi người khác!");
        }
        
        com.fpoly.oe.dao.ChannelRequestDAO channelDAO = new com.fpoly.oe.dao.ChannelRequestDAO();
        com.fpoly.oe.entities.ChannelRequest channelReq = channelDAO.findByUserId(currentUser.getId());
        req.setAttribute("channelRequest", channelReq);
        
        req.getRequestDispatcher("/views/user/edit-profile.jsp").forward(req, resp);
    }
}

