package com.fpoly.oe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpoly.oe.dao.CategoryDAO;
import com.fpoly.oe.entities.Category;

@ExtendWith(MockitoExtension.class)
@Tag("smoke")
public class AdminCategoryControllerTest {

    private AdminCategoryController controller;

    @Mock
    private CategoryDAO dao;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private ServletContext servletContext;

    @BeforeEach
    void setUp() {
        controller = new AdminCategoryController();
        controller.setCategoryDAO(dao);

        lenient().when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        lenient().when(request.getServletContext()).thenReturn(servletContext);
        lenient().when(dao.countAll()).thenReturn(5L);
        lenient().when(dao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        lenient().when(dao.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    @DisplayName("TC_CAT_01: Thêm mới thất bại khi để trống mã danh mục")
    void testCreateCategory_EmptyId() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/create");
        when(request.getParameter("id")).thenReturn("");
        when(request.getParameter("name")).thenReturn("Thể Thao");

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Vui lòng nhập mã danh mục!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_02: Thêm mới thất bại khi mã danh mục không phải là số")
    void testCreateCategory_IdNotNumber() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/create");
        when(request.getParameter("id")).thenReturn("ABC");
        when(request.getParameter("name")).thenReturn("Âm Nhạc");

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Mã danh mục phải là một con số!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_03: Thêm mới thất bại khi để trống tên danh mục")
    void testCreateCategory_EmptyName() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/create");
        when(request.getParameter("id")).thenReturn("50");
        when(request.getParameter("name")).thenReturn("   ");

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Vui lòng nhập tên danh mục!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_04: Thêm mới thất bại khi trùng mã danh mục đã có")
    void testCreateCategory_DuplicateId() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/create");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Phim Hoạt Hình");

        Category existing = new Category(1L, "Phim");
        when(dao.findById(1L)).thenReturn(existing);

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Mã danh mục này đã tồn tại! Vui lòng nhập mã khác.");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_05: Thêm mới danh mục thành công với dữ liệu hợp lệ")
    void testCreateCategory_Success() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/create");
        when(request.getParameter("id")).thenReturn("999");
        when(request.getParameter("name")).thenReturn("Khoa Học & Đời Sống");

        when(dao.findById(999L)).thenReturn(null);

        controller.doPost(request, response);

        verify(dao).create(any(Category.class));
        verify(request).setAttribute("message", "Thêm danh mục thành công!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_06: Cập nhật tên danh mục thành công")
    void testUpdateCategory_Success() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/update");
        when(request.getParameter("id")).thenReturn("999");
        when(request.getParameter("name")).thenReturn("Khoa Học Vũ Trụ");

        controller.doPost(request, response);

        verify(dao).update(any(Category.class));
        verify(request).setAttribute("message", "Cập nhật danh mục thành công!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_07: Cập nhật thất bại khi để trống tên danh mục")
    void testUpdateCategory_EmptyName() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/update");
        when(request.getParameter("id")).thenReturn("999");
        when(request.getParameter("name")).thenReturn("");

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Vui lòng nhập tên danh mục!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_08: Xóa thất bại khi danh mục đang có Video liên kết")
    void testDeleteCategory_ConstraintViolation() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/delete");
        when(request.getParameter("id")).thenReturn("1");

        doThrow(new RuntimeException("Foreign Key Constraint")).when(dao).delete(1L);

        controller.doPost(request, response);

        verify(request).setAttribute("error", "Không thể xóa danh mục này vì đang có video liên kết!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_09: Xóa danh mục trống thành công")
    void testDeleteCategory_Success() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category/delete");
        when(request.getParameter("id")).thenReturn("999");

        controller.doPost(request, response);

        verify(dao).delete(999L);
        verify(request).setAttribute("message", "Xóa danh mục thành công!");
        verify(dispatcher).forward(request, response);
    }

    @Test
    @DisplayName("TC_CAT_10: Tải danh sách và phân trang qua phương thức doGet")
    void testDoGet_LoadPagination() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/category");
        when(request.getParameter("page")).thenReturn("0");

        controller.doGet(request, response);

        verify(dao).countAll();
        verify(dao).findAll(0, 10);
        verify(request).setAttribute(eq("categories"), any());
        verify(request).setAttribute("currentPage", 0);
        verify(dispatcher).forward(request, response);
    }
}
