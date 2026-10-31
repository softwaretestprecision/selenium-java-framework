package com.softwaretestprecision.framework.pages;

import com.softwaretestprecision.framework.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME = testId("username");
    private static final By PASSWORD = testId("password");
    private static final By LOGIN_BUTTON = testId("login-button");
    private static final By ERROR = testId("error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.get("baseUrl"));
        visible(USERNAME);
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        submit(username, password);
        return new InventoryPage(driver).waitUntilLoaded();
    }

    public LoginPage loginExpectingError(String username, String password) {
        submit(username, password);
        visible(ERROR);
        return this;
    }

    public String errorMessage() {
        return text(ERROR);
    }

    private void submit(String username, String password) {
        type(USERNAME, username);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
    }
}
