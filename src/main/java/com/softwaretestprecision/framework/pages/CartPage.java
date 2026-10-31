package com.softwaretestprecision.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CartPage extends BasePage {

    private static final By ITEM_NAMES = testId("inventory-item-name");
    private static final By CHECKOUT = testId("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilLoaded() {
        waitForUrl("cart.html");
        visible(CHECKOUT);
        return this;
    }

    public List<String> itemNames() {
        return isPresent(ITEM_NAMES) ? texts(ITEM_NAMES) : List.of();
    }

    public CheckoutPage checkout() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }
}
