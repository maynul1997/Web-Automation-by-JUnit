import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Scrap Data from DSE Webpage Table")
public class WebScrapingTest {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterAll
    public void teardown() {
        driver.quit();
    }

    @Test
    public void DataScrapingTest() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/src/test/resources/ScrapedData.txt"))) {
            List<WebElement> headertext = driver.findElements(By.xpath("//th[not(contains(@class, 'floatThead-col'))]"));
            for (WebElement header : headertext) {
                String Allheadertext = header.getText().trim();
                if (!headertext.isEmpty()) {
                    System.out.print(Allheadertext + " ");
                    writer.write((Allheadertext + " "));
                }
            }

            WebElement table = driver.findElement(By.className("table-responsive"));
            List<WebElement> allRows = table.findElements(By.tagName("tr"));
            for (WebElement row : allRows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    System.out.print(cell.getText() + " ");
                    writer.write(cell.getText() + " ");
                }
                System.out.println();
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

    }
}
