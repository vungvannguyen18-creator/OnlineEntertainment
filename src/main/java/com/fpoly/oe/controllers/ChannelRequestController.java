package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.dao.ChannelRequestDAO;
import com.fpoly.oe.entities.ChannelRequest;
import com.fpoly.oe.entities.User;

@WebServlet("/channel/request")
public class ChannelRequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        ChannelRequestDAO dao = new ChannelRequestDAO();
        ChannelRequest existing = dao.findByUserId(user.getId());
        
        if (existing != null) {
            if ("REJECTED".equals(existing.getStatus())) {
                existing.setStatus("PENDING");
                existing.setRequestDate(new Date());
                dao.update(existing);
            }
        } else {
            ChannelRequest newRequest = new ChannelRequest();
            newRequest.setUser(user);
            newRequest.setStatus("PENDING");
            dao.create(newRequest);
        }
        
        resp.sendRedirect(req.getContextPath() + "/edit-profile");
    }
}

