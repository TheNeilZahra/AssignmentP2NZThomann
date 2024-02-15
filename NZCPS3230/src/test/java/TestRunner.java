import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "stepdefinitions", // Path to the package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Plugins for report generation
        monochrome = true
)
public class  TestRunner {
    // No need to add any code inside the class body
}
