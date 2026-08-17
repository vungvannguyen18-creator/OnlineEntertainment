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
        
        String activeTab = "list"; 
        
        if (uri.contains("/edit")) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    Long id = Long.parseLong(idStr);
                    formCategory = dao.findById(id);
                    activeTab = "edition";
                    req.setAttribute("isEdit", true);
                } catch (NumberFormatException e) {
                }
            }
        }
        
        loadPagination(req, dao);
        
        req.setAttribute("formCategory", formCategory);
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
                }
            }
            String name = req.getParameter("name");
            
            if (!uri.contains("/delete")) {
                String errorMsg = null;
                
                if (uri.contains("/create")) {
                    if (idStr == null || idStr.trim().isEmpty()) {
                        errorMsg = "Vui lòng nhập mã danh mục!";
                    } else if (category.getId() == null) {
                        errorMsg = "Mã danh mục phải là một con số!";
                    }
                }
                
                if (errorMsg == null && (name == null || name.trim().isEmpty())) {
                    errorMsg = "Vui lòng nhập tên danh mục!";
                }

                if (errorMsg != null) {
                    req.setAttribute("error", errorMsg);
                    req.setAttribute("activeTab", "edition");
                    req.setAttribute("formCategory", category);
                    loadPagination(req, dao);
                    req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
                    return;
                }
            }
            category.setName(name);
            
            String activeTab = "edition";
            
            if (uri.contains("/create")) {
                Category existing = dao.findById(category.getId());
                if (existing != null) {
                    req.setAttribute("error", "Mã danh mục này đã tồn tại! Vui lòng nhập mã khác.");
                    req.setAttribute("activeTab", "edition");
                    req.setAttribute("formCategory", category);
                    loadPagination(req, dao);
                    req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
                    return;
                }
                dao.create(category);
                req.setAttribute("message", "Thêm danh mục thành công!");
                category = new Category();
                activeTab = "list";
            } else if (uri.contains("/update")) {
                dao.update(category);
                req.setAttribute("message", "Cập nhật danh mục thành công!");
                category = new Category();
                activeTab = "list";
            } else if (uri.contains("/delete")) {
                dao.delete(category.getId());
                req.setAttribute("message", "Xóa danh mục thành công!");
                category = new Category();
                activeTab = "list";
            }
            
            req.setAttribute("formCategory", category);
            req.setAttribute("activeTab", activeTab);
            
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Lỗi thao tác dữ liệu: " + e.getMessage();
            if (uri.contains("/delete")) {
                errorMsg = "Không thể xóa danh mục này vì đang có video liên kết!";
            }
            req.setAttribute("error", errorMsg);
            req.setAttribute("activeTab", "edition");
        }
        
        loadPagination(req, dao);
        req.getServletContext().setAttribute("globalCategories", dao.findAll());
        
        req.getRequestDispatcher("/views/admin/category.jsp").forward(req, resp);
    }
    
    private void loadPagination(HttpServletRequest req, CategoryDAO dao) {
        int page = 0;
        String pageStr = req.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (Exception e) {}
        }
        int pageSize = 10;
        long totalCategories = dao.countAll();
        int totalPages = (int) Math.ceil((double) totalCategories / pageSize);
        if (page < 0) page = 0;
        if (page >= totalPages && totalPages > 0) page = totalPages - 1;
        
        List<Category> categories = dao.findAll(page, pageSize);
        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalCount", totalCategories);
    }
}

