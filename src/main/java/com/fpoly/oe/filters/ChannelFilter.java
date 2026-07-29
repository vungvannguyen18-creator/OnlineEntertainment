package com.fpoly.oe.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fpoly.oe.entities.User;

@WebFilter({"/channel/*"})
public class ChannelFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        User user = (User) session.getAttribute("user");
        
        // Filter kiểm tra vai trò và user id có hợp lệ không? (Nếu không quay về login)
        if (user == null || user.getId() == null) {
            session.setAttribute("securityUri", req.getRequestURI());
            req.setAttribute("error", "Vui lòng đăng nhập để thực hiện chức năng này!");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
            return;
        }
        
        // Kiểm tra xem user có phải admin hoặc đã được duyệt channel chưa
        // Ngoại trừ URL /channel/request vì đây là URL dùng để xin cấp quyền
        if (!user.isAdmin() && !req.getRequestURI().contains("/channel/request")) {
            com.fpoly.oe.dao.ChannelRequestDAO channelDAO = new com.fpoly.oe.dao.ChannelRequestDAO();
            com.fpoly.oe.entities.ChannelRequest channelReq = channelDAO.findByUserId(user.getId());
            
            if (channelReq == null || !"APPROVED".equals(channelReq.getStatus())) {
                req.setAttribute("error", "Kênh của bạn chưa được kích hoạt. Vui lòng đăng ký ở phần Cập Nhật Tài Khoản!");
                req.getRequestDispatcher("/views/user/edit-profile.jsp").forward(req, resp);
                return;
            }
        }
        
        // Hợp lệ thì cho đi tiếp
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
