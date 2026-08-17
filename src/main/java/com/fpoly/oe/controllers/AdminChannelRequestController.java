package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.ChannelRequestDAO;
import com.fpoly.oe.entities.ChannelRequest;

@WebServlet({"/admin/channel-request", "/admin/channel-request/approve", "/admin/channel-request/reject"})
public class AdminChannelRequestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChannelRequestDAO dao = new ChannelRequestDAO();
        List<ChannelRequest> requests = dao.findAllPending();
        req.setAttribute("channelRequests", requests);
        
        req.getRequestDispatcher("/views/admin/channel-request.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String reqIdStr = req.getParameter("id");
        
        if (reqIdStr != null) {
            try {
                Long id = Long.parseLong(reqIdStr);
                ChannelRequestDAO dao = new ChannelRequestDAO();
                ChannelRequest channelReq = dao.findById(id);
                
                if (channelReq != null) {
                    if (uri.contains("/approve")) {
                        channelReq.setStatus("APPROVED");
                        req.setAttribute("message", "Đã DUYỆT yêu cầu tạo kênh cho user: " + channelReq.getUser().getId());
                    } else if (uri.contains("/reject")) {
                        channelReq.setStatus("REJECTED");
                        req.setAttribute("message", "Đã TỪ CHỐI yêu cầu tạo kênh cho user: " + channelReq.getUser().getId());
                    }
                    dao.update(channelReq);
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Lỗi xử lý yêu cầu!");
            }
        }
        
        doGet(req, resp);
    }
}

