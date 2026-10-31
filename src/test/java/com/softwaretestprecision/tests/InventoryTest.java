package com.softwaretestprecision.tests;

import com.softwaretestprecision.framework.pages.InventoryPage;
import com.softwaretestprecision.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void showsSixProducts() {
        Assert.assertEquals(loginAsStandardUser().productNames().size(), 6);
    }

    @Test
    public void sortsByPriceHighToLow() {
        List<Double> prices = loginAsStandardUser().sortBy("hilo").prices();
        List<Double> expected = new ArrayList<>(prices);
        expected.sort(Comparator.reverseOrder());
        Assert.assertEquals(prices, expected);
    }

    @Test
    public void cartBadgeTracksAddAndRemove() {
        InventoryPage inventory = loginAsStandardUser()
                .addToCart("Sauce Labs Backpack")
                .addToCart("Sauce Labs Fleece Jacket");
        Assert.assertEquals(inventory.cartCount(), 2);
        inventory.removeFromCart("Sauce Labs Backpack");
        Assert.assertEquals(inventory.cartCount(), 1);
    }
}
