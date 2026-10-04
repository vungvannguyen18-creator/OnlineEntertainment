package com.fpoly.oe.automation.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fpoly.oe.automation.pages.CategoryPage;
import com.fpoly.oe.dao.CategoryDAO;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Kịch bản kiểm thử tự động CRUD Danh mục sử dụng Selenium WebDriver + TestNG
 */
public class CategoryCRUDTest {

    private WebDriver driver;
    private CategoryPage categoryPage;
    private final String BASE_URL = "http://localhost:8080/OnlineEntertainment/admin/category?bypass=true";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // Dọn dẹp trước dữ liệu ID 999 nếu có từ các lần chạy trước
        try {
            new CategoryDAO().delete(999L);
        } catch (Exception ignored) {
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        String isHeadless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(isHeadless)) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.manage().window().maximize();

        categoryPage = new CategoryPage(driver);
    }

    @Test(priority = 1, groups = {"smoke", "crud"}, description = "TC_CAT_01: Kiểm tra lỗi khi để trống mã danh mục")
    public void testCreateCategory_EmptyFields() {
        driver.get(BASE_URL);
        categoryPage.fillForm("", "Phim Mới");
        categoryPage.clickCreate();

        String errorMsg = categoryPage.getDangerAlertText();
        Assert.assertTrue(errorMsg.contains("Vui lòng nhập mã danh mục"),
                "Thông báo lỗi mong đợi phải chứa 'Vui lòng nhập mã danh mục', thực tế nhận: " + errorMsg);
    }

    @Test(priority = 2, groups = {"smoke", "crud"}, description = "TC_CAT_05: Thêm mới danh mục thành công")
    public void testCreateCategory_Success() {
        driver.get(BASE_URL);
        categoryPage.fillForm("999", "Thể Thao Điện Tử");
        categoryPage.clickCreate();

        String successMsg = categoryPage.getSuccessAlertText();
        Assert.assertTrue(successMsg.contains("Thêm danh mục thành công"),
                "Thông báo mong đợi 'Thêm danh mục thành công', thực tế nhận: " + successMsg);
    }

    @Test(priority = 3, groups = {"crud"}, description = "TC_CAT_06: Cập nhật tên danh mục thành công")
    public void testUpdateCategory_Success() {
        // Kích hoạt chế độ sửa danh mục ID 999 để bật nút Cập nhật
        driver.get("http://localhost:8080/OnlineEntertainment/admin/category/edit?id=999");
        categoryPage.enterName("Esports Việt Nam");
        categoryPage.clickUpdate();

        String successMsg = categoryPage.getSuccessAlertText();
        Assert.assertTrue(successMsg.contains("Cập nhật danh mục thành công"),
                "Thông báo mong đợi 'Cập nhật danh mục thành công', thực tế nhận: " + successMsg);
    }

    @Test(priority = 4, groups = {"crud"}, description = "TC_CAT_09: Xóa danh mục thành công")
    public void testDeleteCategory_Success() {
        // Kích hoạt chế độ sửa danh mục ID 999 để bật nút Xóa
        driver.get("http://localhost:8080/OnlineEntertainment/admin/category/edit?id=999");
        categoryPage.clickDelete();

        String successMsg = categoryPage.getSuccessAlertText();
        Assert.assertTrue(successMsg.contains("Xóa danh mục thành công"),
                "Thông báo mong đợi 'Xóa danh mục thành công', thực tế nhận: " + successMsg);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
