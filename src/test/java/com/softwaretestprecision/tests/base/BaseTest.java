package com.softwaretestprecision.tests.base;

import com.softwaretestprecision.framework.config.Config;
import com.softwaretestprecision.framework.driver.DriverFactory;
import com.softwaretestprecision.framework.pages.InventoryPage;
import com.softwaretestprecision.framework.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.startDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    protected LoginPage loginPage() {
        return new LoginPage(driver()).open();
    }

    protected InventoryPage loginAsStandardUser() {
        return loginPage().loginAs(Config.get("standardUser"), Config.get("password"));
    }
}
