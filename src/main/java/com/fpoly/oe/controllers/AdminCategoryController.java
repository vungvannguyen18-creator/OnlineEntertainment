package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.CategoryDAO;
import com.fpoly.oe.entities.Category;

@WebServlet({"/admin/category", "/admin/category/create", "/admin/category/update", "/admin/category/delete", "/admin/category/edit"})
public class AdminCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        CategoryDAO dao = new CategoryDAO();
        Category formCategory = new Category();
        
        String activeTab = "list"; // Mặc định hiển thị danh sách
        
        if (uri.contains("/edit")) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    Long id = Long.parseLong(idStr);
                    formCategory = dao.findById(id);
                    activeTab = "edition";
                } catch (NumberFormatException e) {
                    // Ignore or handle
                }
            }
        }
        
        List<Category> categories = dao.findAll();
        
        req.setAttribute("formCategory", formCategory);
        req.setAttribute("categories", categories);
        req.setAttribute("activeTab", activeTab);
        
        req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String uri = req.getRequestURI();
        CategoryDAO dao = new CategoryDAO();
        
        try {
            Category category = new Category();
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    category.setId(Long.parseLong(idStr));
                } catch (NumberFormatException e) {
                    // Ignore for create
                }
            }
            category.setName(req.getParameter("name"));
            
            if (uri.contains("/create")) {
                dao.create(category);
                req.setAttribute("message", "Thêm danh mục thành công!");
            } else if (uri.contains("/update")) {
                dao.update(category);
                req.setAttribute("message", "Cập nhật danh mục thành công!");
            } else if (uri.contains("/delete")) {
                dao.delete(category.getId());
                req.setAttribute("message", "Xóa danh mục thành công!");
                category = new Category(); // Xóa xong form trống
            }
            
            req.setAttribute("formCategory", category);
            req.setAttribute("activeTab", "edition");
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi thao tác dữ liệu: " + e.getMessage());
            req.setAttribute("activeTab", "edition");
        }
        
        List<Category> categories = dao.findAll();
        req.setAttribute("categories", categories);
        // Làm mới bộ nhớ đệm danh mục toàn cục
        req.getServletContext().setAttribute("globalCategories", categories);
        
        req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
    }
}
