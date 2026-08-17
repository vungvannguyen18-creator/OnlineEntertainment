package com.fpoly.oe.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import com.fpoly.oe.dao.CategoryDAO;

@WebFilter("/*")
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        
        if (req.getServletContext().getAttribute("globalCategories") == null) {
            CategoryDAO dao = new CategoryDAO();
            req.getServletContext().setAttribute("globalCategories", dao.findAll());
        }
        
        chain.doFilter(request, response);
    }
}

