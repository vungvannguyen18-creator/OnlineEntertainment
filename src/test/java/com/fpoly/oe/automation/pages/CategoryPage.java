package com.fpoly.oe.automation.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object Model (POM) cho màn hình Quản lý Danh mục (views/admin/category.jsp)
 */
public class CategoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By idInput = By.name("id");
    private By nameInput = By.name("name");
    private By btnCreate = By.xpath("//button[contains(@formaction, '/create')]");
    private By btnUpdate = By.xpath("//button[contains(@formaction, '/update')]");
    private By btnDelete = By.xpath("//button[contains(@formaction, '/delete')]");
    private By btnReset = By.xpath("//a[contains(@href, '/admin/category') and contains(text(), 'Làm mới')]");

    private By alertSuccess = By.cssSelector(".alert-success");
    private By alertDanger = By.cssSelector(".alert-danger");

    private By tabEdition = By.id("edition-tab");
    private By tabList = By.id("list-tab");
    private By tableRows = By.cssSelector("#list table tbody tr");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    public void ensureEditionTab() {
        try {
            WebElement tab = driver.findElement(tabEdition);
            if (!tab.getAttribute("class").contains("active")) {
                tab.click();
                Thread.sleep(400);
            }
        } catch (Exception ignored) {
        }
    }

    public void enterId(String id) {
        ensureEditionTab();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(idInput));
        element.clear();
        if (id != null && !id.isEmpty()) {
            element.sendKeys(id);
        }
    }

    public void enterName(String name) {
        ensureEditionTab();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        element.clear();
        if (name != null && !name.isEmpty()) {
            element.sendKeys(name);
        }
    }

    public void fillForm(String id, String name) {
        enterId(id);
        enterName(name);
    }

    public void clickCreate() {
        ensureEditionTab();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnCreate));
        btn.click();
    }

    public void clickUpdate() {
        ensureEditionTab();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnUpdate));
        btn.click();
    }

    public void clickDelete() {
        ensureEditionTab();
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnDelete));
        btn.click();
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception ignored) {
        }
    }

    public void switchToTabList() {
        driver.findElement(tabList).click();
    }

    public void switchToTabEdition() {
        driver.findElement(tabEdition).click();
    }

    public String getSuccessAlertText() {
        try {
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertSuccess));
            return alert.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getDangerAlertText() {
        try {
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertDanger));
            return alert.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isCategoryPresentInTable(String id) {
        switchToTabList();
        List<WebElement> rows = driver.findElements(tableRows);
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (!cols.isEmpty() && cols.get(0).getText().trim().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
