package um.edu.mt.model;

import org.openqa.selenium.WebDriver;
import um.edu.mt.model.actions.*;
import um.edu.mt.utils.WebDriverUtils;

public class ThomannModel {
    private final WebDriver driver;
    private final LoginAction loginAction;
    private final SearchAction searchAction;
    private final AddToCartAction addToCartAction;
    private final RemoveFromCartAction removeFromCartAction;

    private String state = "Homepage";

    public ThomannModel(WebDriver driver) {
        this.driver = driver;
        this.loginAction = new LoginAction(driver);
        this.searchAction = new SearchAction(driver);
        this.addToCartAction = new AddToCartAction(driver);
        this.removeFromCartAction = new RemoveFromCartAction(driver);
    }

    // Log in
    public void login(String username, String password) {
        if ("Homepage".equals(state)) {
            loginAction.login(username, password);
            state = "LoggedIn";
        }
    }

    // Search for a product
    public void search(String query) {
        if ("Homepage".equals(state) || "LoggedIn".equals(state)) {
            searchAction.search(query);
            state = "SearchResults";
        }
    }

    // Add a product to the cart
    public void addToCart() {
        if ("Homepage".equals(state) || "SearchResults".equals(state)) {
            addToCartAction.addToCart();
            state = "ProductAdded";
        }
    }

    // Remove a product from the cart
    public void removeFromCart() {
        if ("ProductAdded".equals(state)) {
            removeFromCartAction.removeFromCart();
            state = "ItemRemoved";
        }
    }

    // Reset method to return to the initial state, if needed
    public void reset() {
        driver.get("https://www.thomann.de"); // Navigate back to the homepage
        WebDriverUtils.acceptCookies(driver);
        state = "Homepage";
    }

    public String getState() {
        return state;
    }

    public void clickFirstProduct() {
        new ClickFirstProductAction(driver).clickFirstProduct();
        state = "ProductSelected";
    }
}
