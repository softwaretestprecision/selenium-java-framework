package com.softwaretestprecision.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final By TITLE = testId("title");
    private static final By ITEM_NAMES = testId("inventory-item-name");
    private static final By ITEM_PRICES = testId("inventory-item-price");
    private static final By SORT = testId("product-sort-container");
    private static final By CART_BADGE = testId("shopping-cart-badge");
    private static final By CART_LINK = testId("shopping-cart-link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage waitUntilLoaded() {
        waitForUrl("inventory.html");
        visible(TITLE);
        return this;
    }

    public String title() {
        return text(TITLE);
    }

    public List<String> productNames() {
        return texts(ITEM_NAMES);
    }

    public List<Double> prices() {
        return texts(ITEM_PRICES).stream().map(p -> Double.parseDouble(p.replace("$", ""))).toList();
    }

    public InventoryPage sortBy(String value) {
        new Select(visible(SORT)).selectByValue(value);
        return this;
    }

    /** "Sauce Labs Backpack" -> data-test="add-to-cart-sauce-labs-backpack" */
    public InventoryPage addToCart(String productName) {
        click(testId("add-to-cart-" + slug(productName)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(testId("remove-" + slug(productName))));
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        click(testId("remove-" + slug(productName)));
        return this;
    }

    public int cartCount() {
        return isPresent(CART_BADGE) ? Integer.parseInt(text(CART_BADGE)) : 0;
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver).waitUntilLoaded();
    }

    private static String slug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9.()]+", "-");
    }
}
