package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import um.edu.mt.utils.WebDriverUtils;

import java.time.Duration;
import java.util.List;

public class NavigationTests {

    private void acceptCookiesIfNeeded() {
        try {
            // Example: Adjust the selector based on the actual attribute of the cookie acceptance button
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Accept')]")));
            acceptCookiesButton.click();
            System.out.println("Cookies accepted.");
        } catch (Exception e) {
            // If the button is not found or not clickable, it might not be present, or cookies have already been accepted.
            System.out.println("No need to accept cookies, or the button was not found.");
        }
    }

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        this.driver = WebDriverUtils.initializeWebDriver(); // Use the static method to initialize the WebDriver
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Quit the driver after the test is done to close the browser
        }
    }

    @Test
    public void categoryNavigationTest() {
        // Navigate to the homepage
        driver.get("https://www.thomann.de/intl/"); // Make sure you're on the international version if needed

        // Accept cookies to clear any modal that might block category selection
        WebDriverUtils.acceptCookies(driver);

        // Updated XPath to click on the "Drums" category, using the link text for precision
        String xpathForDrumsCategory = "//a[contains(@class, 'categories-list__link') and text()='Drums']";
        WebElement categoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForDrumsCategory)));
        categoryLink.click();

        // Verify that the driver has navigated to the "Drums" category page
        assertTrue(driver.getTitle().toLowerCase().contains("drums"), "Title does not contain 'drums'");

        // Verify that a minimum number of products are displayed on the page
        int numProductsRequired = 5;
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//img[contains(@class, 'product-image')]"), numProductsRequired - 1));
        List<WebElement> productImages = driver.findElements(By.xpath("//img[contains(@class, 'product-image')]"));
        assertTrue(productImages.size() >= numProductsRequired, "There are less than " + numProductsRequired + " products on the category page.");
    }

}
