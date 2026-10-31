package com.softwaretestprecision.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private static final By FIRST_NAME = testId("firstName");
    private static final By LAST_NAME = testId("lastName");
    private static final By POSTAL_CODE = testId("postalCode");
    private static final By CONTINUE = testId("continue");
    private static final By SUBTOTAL = testId("subtotal-label");
    private static final By FINISH = testId("finish");
    private static final By COMPLETE_HEADER = testId("complete-header");
    private static final By ERROR = testId("error");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterCustomer(String first, String last, String zip) {
        type(FIRST_NAME, first);
        type(LAST_NAME, last);
        type(POSTAL_CODE, zip);
        click(CONTINUE);
        return this;
    }

    public CheckoutPage continueWithoutDetails() {
        click(CONTINUE);
        return this;
    }

    public String subtotal() {
        return text(SUBTOTAL);
    }

    public CheckoutPage finish() {
        click(FINISH);
        return this;
    }

    public String confirmation() {
        return text(COMPLETE_HEADER);
    }

    public String error() {
        return text(ERROR);
    }
}
