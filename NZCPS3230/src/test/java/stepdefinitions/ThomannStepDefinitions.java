package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThomannStepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "N:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Given("I am on the Thomann homepage")
    public void i_am_on_the_thomann_homepage() {
        driver.get("https://www.thomann.de/intl/index.html");
        acceptCookies();
    }

    private void acceptCookies() {
        try {
            // Attempt to find the cookie acceptance button by its class name and click it
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".consent-button.js-accept-all-cookies")));
            acceptCookiesButton.click();
            System.out.println("Cookies accepted.");
        } catch (TimeoutException e) {
            System.out.println("Cookie consent dialog did not appear or was already accepted.");
        } catch (NoSuchElementException e) {
            System.err.println("Cookie consent button not found.");
        }
    }


    @And("I navigate to the login page")
    public void i_navigate_to_the_login_page() {
        try {
            WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[aria-label='Customer Centre']")));
            loginLink.click();
            System.out.println("Navigated to login page.");
        } catch (TimeoutException e) {
            System.err.println("Failed to navigate to the login page: " + e.getMessage());
        }
    }


    @And("I log in with valid credentials")
    public void i_log_in_with_valid_credentials() {
        i_navigate_to_the_login_page(); // Ensure you're on the login page first

        // Wait for the email input field to be visible and enter the email address
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uname"))).sendKeys("neil.zahra.21@um.edu.mt");

        // Wait for the password input field to be visible and enter the password
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passw"))).sendKeys("TestPassword123?");

        // Click the login button to submit the form
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.fx-button.fx-button--primary"))).click();

        System.out.println("Logged in successfully.");
    }


    @When("I click on the {string} category")
    public void i_click_on_the_category(String categoryName) {
        try {
            WebElement categoryLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'categories-list__link') and normalize-space()='" + categoryName + "']")));
            categoryLink.click();
        } catch (StaleElementReferenceException e) {
            // Element went stale, try to find and click it again
            WebElement categoryLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@class, 'categories-list__link') and normalize-space()='" + categoryName + "']")));
            categoryLink.click();
        }
    }




    @Then("I should be taken to the {string} category page")
    public void i_should_be_taken_to_the_category_page(String categoryName) {
        assertTrue(driver.getTitle().contains(categoryName));
    }

    @And("the category should show at least {int} products")
    public void the_category_should_show_at_least_products(int numProducts) {
        // Wait for the images of the products to be loaded
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//img[contains(@class, 'product-image')]"), numProducts - 1));

        // Get all the product images
        List<WebElement> productImages = driver.findElements(By.xpath("//img[contains(@class, 'product-image')]"));
        assertTrue("There are less than " + numProducts + " products on the category page.", productImages.size() >= numProducts);
    }


    @When("I search for a product using the term {string}")
    public void i_search_for_a_product_using_the_term(String searchTerm) {
        boolean searchPerformed = false;
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;

        while (!searchPerformed && attempts < MAX_ATTEMPTS) {
            try {
                // Locate the search input box by its id immediately before sending keys to it.
                WebElement searchBox = driver.findElement(By.id("fsearch-sw"));
                searchBox.clear(); // Clear any pre-existing text in the search box.
                searchBox.sendKeys(searchTerm);
                searchBox.sendKeys(Keys.ENTER); // Assuming hitting Enter triggers the search.
                searchPerformed = true; // Set flag to true indicating search has been performed.
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Trying to recover from a stale element :" + e.getMessage());
            }
        }

        if (!searchPerformed) {
            throw new AssertionError("Could not perform search operation after " + MAX_ATTEMPTS + " attempts.");
        }
    }



    @Then("I should see the search results")
    public void i_should_see_the_search_results() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".fx-product-list-entry")));
        // Assuming ".fx-product-list-entry" is a class common to all search result items
    }



    @And("there should be at least {int} products in the search results")
    public void there_should_be_at_least_products_in_the_search_results(int expectedProductCount) {
        // Update the CSS selector to accurately select product items in the search results based on the provided structure.
        List<WebElement> productItems = driver.findElements(By.cssSelector(".fx-product-list-entry"));
        assertTrue("There are less than " + expectedProductCount + " products in the search results.", productItems.size() >= expectedProductCount);
    }


    @When("I click on the first product in the results")
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








    @Then("I should be taken to the details page for that product")
    public void i_should_be_taken_to_the_details_page_for_that_product() {
        // Wait for a specific element that indicates the product details page is loaded
        // This could be a product title, a unique class or ID associated with the product details layout
        // Replace 'uniqueProductDetailElement' with the actual selector for the element that confirms the product detail page.
        By uniqueProductDetailElement = By.xpath("//h1[contains(@class, 'product-title') or @id='product-title']"); // Example XPath
        wait.until(ExpectedConditions.visibilityOfElementLocated(uniqueProductDetailElement));

        // Check if the product title or any unique detail element is displayed
        WebElement productTitleElement = driver.findElement(uniqueProductDetailElement);
        assertTrue("The product details page did not load correctly.", productTitleElement.isDisplayed());
    }


    @After
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }
}
