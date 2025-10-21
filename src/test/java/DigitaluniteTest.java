import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check the form submitted successfully")
public class DigitaluniteTest {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void FormSubmit() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        //Handle accept cookies
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        Thread.sleep(1000);
        //Find elements of form input fields and give input
        driver.findElement(By.id("edit-name")).sendKeys("Faisal Kabir");

        driver.findElement(By.id("edit-number")).sendKeys("01712345678");

        driver.findElement(By.id("edit-date")).sendKeys("04/14/2025");

        driver.findElement(By.id("edit-email")).sendKeys("onlyfaisalkabir@gmail.com");

        driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-")).sendKeys("I'm an automation testing learner with a focus on writing clear and effective test cases and automated test scripts. I'm gaining hands-on experience with tools like Selenium, JUnit, and Gradle, and enjoy exploring ways to improve test coverage and efficiency. Passionate about quality assurance, I’m continuously building my skills to contribute to reliable and high-performing software.");
        Thread.sleep(1000);
        //Scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        //Upload Image
        WebElement uploadElement = driver.findElement(By.id("edit-uploadocument-upload"));
        String filePath = System.getProperty("user.dir") + "/src/test/resources/test.jpg";
        uploadElement.sendKeys(filePath);
        Thread.sleep(3000);
        //Check box click
        WebElement checkBoxElement = driver.findElement(By.cssSelector("label[for='edit-age']"));
        checkBoxElement.click();

        // Find the submit button and click it
        WebElement submitButton = driver.findElement(By.id("edit-submit"));
        submitButton.click();
        Thread.sleep(2000);

        //Assertion
        String confirmationMessage = driver.findElement(By.id("block-pagetitle-2")).getText();
        Assertions.assertTrue(confirmationMessage.contains("Thank you for your submission!"));
    }

    @AfterAll
    public void teardown() {

        driver.quit();
    }
}
