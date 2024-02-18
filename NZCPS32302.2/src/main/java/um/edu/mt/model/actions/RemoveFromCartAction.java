package um.edu.mt.model.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RemoveFromCartAction {
    private final WebDriver driver;

    public RemoveFromCartAction(WebDriver driver) {
        this.driver = driver;
    }

    public void removeFromCart() {
        WebElement deleteButton = driver.findElement(By.xpath("//div[@data-testid='delete-action-416143274']"));
        deleteButton.click();

    }
}
