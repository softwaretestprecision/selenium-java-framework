package com.softwaretestprecision.tests;

import com.softwaretestprecision.framework.config.Config;
import com.softwaretestprecision.framework.pages.InventoryPage;
import com.softwaretestprecision.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void standardUserCanLogIn() {
        InventoryPage inventory = loginAsStandardUser();
        Assert.assertEquals(inventory.title(), "Products");
    }

    @Test
    public void lockedOutUserSeesError() {
        String error = loginPage()
                .loginExpectingError(Config.get("lockedUser"), Config.get("password"))
                .errorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out."), error);
    }

    @Test
    public void wrongPasswordSeesError() {
        String error = loginPage()
                .loginExpectingError(Config.get("standardUser"), "wrong-password")
                .errorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"), error);
    }
}
