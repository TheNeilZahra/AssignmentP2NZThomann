package um.edu.mt.model.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchAction {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void acceptCookiesIfNeeded() {
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Accept')]"))); // Adjust XPath as needed
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("Accept cookies button not found or already accepted.");
        }
    }

    public void search(String query) {
        acceptCookiesIfNeeded(); // Ensure cookies are accepted to not obstruct the search functionality

        WebElement searchBox = driver.findElement(By.id("fsearch-sw"));
        searchBox.clear(); // Clear any pre-existing text in the search box
        searchBox.sendKeys(query); // Enter the search query
        searchBox.sendKeys(Keys.ENTER); // Submit the search by pressing Enter
    }
}
