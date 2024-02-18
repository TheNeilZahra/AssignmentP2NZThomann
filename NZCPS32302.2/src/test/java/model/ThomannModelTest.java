package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.edu.mt.model.ThomannModel;
import um.edu.mt.utils.WebDriverUtils;
import um.edu.mt.utils.Constants;

import static org.junit.jupiter.api.Assertions.*;

public class ThomannModelTest {
    private ThomannModel model;

    @BeforeEach
    public void setUp() {
        // Initialize WebDriver and model for each test
        model = new ThomannModel(WebDriverUtils.initializeWebDriver());
    }

    @Test
    public void testLogin() {
        model.login(Constants.TEST_USER_EMAIL, Constants.TEST_USER_PASSWORD);
        assertEquals("LoggedIn", model.getState());
    }

    @Test
    public void testSearch() {
        model.search("guitar");
        assertEquals("SearchResults", model.getState());
    }

    @Test
    public void testAddAndRemoveFromCart() {
        // Select the first product that appears
        model.clickFirstProduct(); // Assume this method updates the model's state to "ProductSelected"
        assertEquals("ProductSelected", model.getState());

        // Then add to cart
        model.addToCart(); // Now, addToCart doesn't need a productId parameter
        assertEquals("ProductAdded", model.getState());

        // And remove from cart
        model.removeFromCart(); // This might also not need a productId if it's clear which product to remove
        assertEquals("ItemRemoved", model.getState());
    }


    @AfterEach
    public void tearDown() {
        // Reset the model and close the browser after each test
        model.reset();
    }
}
