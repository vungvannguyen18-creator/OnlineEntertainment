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

@WebFilter({"/admin/*"})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        User user = (User) session.getAttribute("user");
        
        // Kiểm tra nếu chưa đăng nhập hoặc không phải admin
        if (user == null || !user.isAdmin()) {
            session.setAttribute("securityUri", req.getRequestURI());
            req.setAttribute("error", "Bạn không có quyền truy cập vào khu vực quản trị!");
            req.getRequestDispatcher("/views/user/login.jsp").forward(req, resp);
            return;
        }
        
        // Nếu là admin thì cho qua
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
