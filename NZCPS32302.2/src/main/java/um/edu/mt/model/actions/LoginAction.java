package um.edu.mt.model.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginAction {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public void acceptCookiesIfPresent() {
        try {
            // Adjust the selector based on the actual cookie consent button on the Thomann website
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".consent-button.js-accept-all-cookies")));
            if (acceptCookiesButton != null) {
                acceptCookiesButton.click();
                System.out.println("Cookies accepted.");
            }
        } catch (Exception e) {
            // If the cookie consent dialog is not present or already accepted, this catch block will handle the exception.
            System.out.println("Cookie consent dialog did not appear, was already accepted, or another issue occurred: " + e.getMessage());
        }
    }

    public LoginAction(WebDriver driver) {
        this.driver = driver;
        // Ensure WebDriverWait is initialized to wait for elements on the page
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public void ensureOnHomePage() {
        driver.get("https://www.thomann.de/intl/index.html"); // Adjust URL as needed
        acceptCookiesIfPresent();
    }

    public void navigateToLoginPage() {
        // Ensure on the homepage before navigating to the login page
        ensureOnHomePage();

        try {
            WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[aria-label='Customer Centre']")));
            loginLink.click();

            // After clicking the login link, wait for a known element on the login page to ensure it has loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uname")));

            System.out.println("Successfully navigated to the login page.");
        } catch (Exception e) {
            System.err.println("Error navigating to login page: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to login page.", e);
        }
    }

    public void login(String username, String password) {
        navigateToLoginPage();

        try {
            driver.findElement(By.id("uname")).sendKeys(username);
            driver.findElement(By.id("passw")).sendKeys(password);
            driver.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();
            System.out.println("Login successful.");
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            throw new RuntimeException("Login process failed.", e);
        }
    }
}
