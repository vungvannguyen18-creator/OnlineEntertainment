package com.fpoly.oe.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpoly.oe.dao.UserDAO;
import com.fpoly.oe.entities.User;

@WebServlet({"/admin/user", "/admin/user/create", "/admin/user/update", "/admin/user/delete", "/admin/user/edit", "/admin/user/lock", "/admin/user/unlock"})
public class AdminUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        UserDAO dao = new UserDAO();
        User formUser = new User();
        
        String activeTab = "userList"; 
        
        if (uri.contains("/edit")) {
            String id = req.getParameter("id");
            if (id != null) {
                formUser = dao.findById(id);
                activeTab = "userEdition";
            }
        }
        
        List<User> users = dao.findAll();
        
        req.setAttribute("formUser", formUser);
        req.setAttribute("users", users);
        req.setAttribute("activeTab", activeTab);
        
        req.getRequestDispatcher("/views/admin/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        UserDAO dao = new UserDAO();
        
        try {
            User user = new User();
            user.setId(req.getParameter("id"));
            user.setPassword(req.getParameter("password"));
            user.setFullname(req.getParameter("fullname"));
            user.setEmail(req.getParameter("email"));
            
            // Validate
            if (!uri.contains("/delete") && !uri.endsWith("/lock") && !uri.endsWith("/unlock")) {
                if (user.getId() == null || user.getId().trim().isEmpty() ||
                    user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                    user.getFullname() == null || user.getFullname().trim().isEmpty() ||
                    user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                    req.setAttribute("error", "Vui lòng nhập đầy đủ Mã, Mật khẩu, Họ tên và Email!");
                    req.setAttribute("activeTab", "userEdition");
                    req.setAttribute("formUser", user);
                    req.setAttribute("users", dao.findAll());
                    req.getRequestDispatcher("/views/admin/user.jsp").forward(req, resp);
                    return;
                }
            }
            
            // Lấy role từ radio button (nếu có)
            String roleStr = req.getParameter("role");
            user.setAdmin("true".equals(roleStr));
            
            if (uri.contains("/create")) {
                dao.create(user);
                req.setAttribute("message", "Thêm người dùng thành công!");
            } else if (uri.contains("/update")) {
                dao.update(user);
                req.setAttribute("message", "Cập nhật người dùng thành công!");
            } else if (uri.contains("/delete")) {
                // Không cho phép tự xóa chính mình nếu đang login
                User sessionUser = (User) req.getSession().getAttribute("user");
                if (sessionUser != null && sessionUser.getId().equals(user.getId())) {
                    req.setAttribute("error", "Không thể tự xóa tài khoản của chính mình!");
                } else {
                    dao.delete(user.getId());
                    req.setAttribute("message", "Xóa người dùng thành công!");
                    user = new User(); // Xóa xong thì form trống
                }
            } else if (uri.endsWith("/lock") || uri.endsWith("/unlock")) {
                User targetUser = dao.findById(user.getId());
                if (targetUser != null) {
                    User sessionUser = (User) req.getSession().getAttribute("user");
                    boolean isSelf = (sessionUser != null && sessionUser.getId().equals(targetUser.getId()));
                    boolean isLockAction = uri.endsWith("/lock");
                    
                    if (isSelf && isLockAction) {
                        req.setAttribute("error", "Không thể tự khóa tài khoản của chính mình!");
                    } else {
                        targetUser.setActive(!isLockAction);
                        dao.update(targetUser);
                        
                        if (isSelf) {
                            sessionUser.setActive(targetUser.getActive());
                            req.getSession().setAttribute("user", sessionUser);
                        }
                        
                        req.setAttribute("message", !isLockAction ? "Đã mở khóa tài khoản!" : "Đã khóa tài khoản!");
                    }
                    user = new User(); // Xóa form
                    req.setAttribute("activeTab", "userList"); // Trở lại tab list
                }
            }
            
            req.setAttribute("formUser", user);
            req.setAttribute("activeTab", "userEdition");
            
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "Lỗi thao tác dữ liệu!";
            if (uri.contains("/delete")) {
                errorMsg = "Không thể xóa người dùng này vì họ đang có dữ liệu liên quan (Video, Lượt thích, Chia sẻ...). Vui lòng Khóa tài khoản thay vì Xóa!";
            } else if (uri.contains("/create")) {
                errorMsg = "Không thể thêm mới! Mã người dùng hoặc Email này có thể đã tồn tại.";
            } else if (uri.contains("/update")) {
                errorMsg = "Không thể cập nhật! Email này có thể đã được người khác sử dụng.";
            }
            req.setAttribute("error", errorMsg);
            req.setAttribute("activeTab", "userEdition");
        }
        
        List<User> users = dao.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/views/admin/user.jsp").forward(req, resp);
    }
}
