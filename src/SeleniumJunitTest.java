import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class SeleniumJunitTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // ทำงานก่อนเริ่มทุกๆ Test Case: เปิด Browser และไปที่ Google
        System.setProperty("webdriver.chrome.driver", "./src/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    @AfterEach
    void tearDown() {
        // ทำงานหลังจบทุกๆ Test Case: ปิด Browser และคืนทรัพยากรระบบทั้งหมด
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void tc_101_search_by_keyword() {
        WebElement search_box = driver.findElement(By.id("APjFqb"));
        search_box.sendKeys("NPRU");
        search_box.sendKeys(Keys.ENTER);
    }

    @Test
    @Disabled("Temporarily disabling this test case")
    void tc_102_search_software() {
        WebElement searchBox = driver.findElement(By.id("APjFqb"));
        searchBox.sendKeys("Software");
        searchBox.sendKeys(Keys.ENTER);
    }
}