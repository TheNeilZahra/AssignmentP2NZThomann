package um.edu.mt.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WebDriverUtils {

    public static WebDriver initializeWebDriver() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "N:\\chromedriver-win64\\chromedriver.exe");

        // Configure ChromeOptions, if necessary
        ChromeOptions options = new ChromeOptions();
        // Example option; adjust as needed
        options.addArguments("--start-maximized");

        // Initialize the WebDriver with ChromeDriver and options
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver, long timeoutInSeconds) {
        // Convert the timeout from seconds to a Duration object
        Duration timeout = Duration.ofSeconds(timeoutInSeconds);
        return new WebDriverWait(driver, timeout);
    }
    public static void acceptCookies(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Attempt to find the cookie acceptance button by its CSS selector and click it
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".consent-button.js-accept-all-cookies")));
            acceptCookiesButton.click();
            System.out.println("Cookies accepted.");
        } catch (Exception e) {
            System.out.println("Cookie consent dialog did not appear, was already accepted, or another issue occurred: " + e.getMessage());
        }
    }
    public void click_on_the_first_product_in_the_results(WebDriver driver) {
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

}
