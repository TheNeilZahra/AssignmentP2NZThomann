package um.edu.mt.model.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddToCartAction {
    private final WebDriver driver;

    public AddToCartAction(WebDriver driver) {
        this.driver = driver;
    }
    /*
    public void i_click_on_the_first_product_in_the_results() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Define XPath selectors for search results and category listings
        String searchResultXPath = "//div[contains(@class, 'fx-product-list-entry')]//a[contains(@class, 'product__image')]";
        String categoryListingXPath = "//a[contains(@class, 'fx-product-box')]";

        WebElement firstProductLink = null;

        // Try to find the first product in search results
        List<WebElement> searchResults = driver.findElements(By.xpath(searchResultXPath));
        if (!searchResults.isEmpty()) {
            firstProductLink = searchResults.get(0);
        } else {
            // If no product found in search results, try finding the first product in category listings
            List<WebElement> categoryListings = driver.findElements(By.xpath(categoryListingXPath));
            if (!categoryListings.isEmpty()) {
                firstProductLink = categoryListings.get(0);
            }
        }

        // Click on the first product link if found
        if (firstProductLink != null) {
            wait.until(ExpectedConditions.elementToBeClickable(firstProductLink)).click();
        } else {
            throw new NoSuchElementException("No product link found in either search results or category listings.");
        }
    }
    */


    public void addToCart() {
        //i_click_on_the_first_product_in_the_results();
        driver.findElement(By.xpath("//button[normalize-space()='Add to Basket']")).click();

    }
}
