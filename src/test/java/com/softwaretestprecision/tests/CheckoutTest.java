package com.softwaretestprecision.tests;

import com.softwaretestprecision.framework.pages.CartPage;
import com.softwaretestprecision.framework.pages.CheckoutPage;
import com.softwaretestprecision.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void completeCheckout() {
        CartPage cart = loginAsStandardUser()
                .addToCart("Sauce Labs Backpack")
                .addToCart("Sauce Labs Onesie")
                .openCart();
        Assert.assertEquals(cart.itemNames(), List.of("Sauce Labs Backpack", "Sauce Labs Onesie"));

        CheckoutPage checkout = cart.checkout().enterCustomer("Ada", "Lovelace", "45801");
        Assert.assertEquals(checkout.subtotal(), "Item total: $37.98");
        Assert.assertEquals(checkout.finish().confirmation(), "Thank you for your order!");
    }

    @Test
    public void checkoutRequiresFirstName() {
        String error = loginAsStandardUser()
                .addToCart("Sauce Labs Bike Light")
                .openCart()
                .checkout()
                .continueWithoutDetails()
                .error();
        Assert.assertTrue(error.contains("First Name is required"), error);
    }
}
