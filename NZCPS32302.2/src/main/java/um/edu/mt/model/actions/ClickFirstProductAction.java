package um.edu.mt.model.actions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ClickFirstProductAction {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ClickFirstProductAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased timeout for waiting
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to trigger any potential lazy-loading
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2)"); // Scroll halfway down as an example

        // Wait explicitly for the first product box to appear in the DOM
        try {
            WebElement firstProductBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".fx-product-box")));

            // Now that we've confirmed at least one product box is present, scroll it into view
            js.executeScript("arguments[0].scrollIntoView(true);", firstProductBox);

            // Ensure it's clickable before proceeding
            wait.until(ExpectedConditions.elementToBeClickable(firstProductBox)).click();
            System.out.println("First product has been clicked.");
        } catch (Exception e) {
            // Catching any exception to provide a clearer message about what might have gone wrong
            System.out.println("Failed to find or click the first product due to: " + e.getMessage());
            throw e; // Re-throwing the exception for further handling or logging
        }
    }




}
