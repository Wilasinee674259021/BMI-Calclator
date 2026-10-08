import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class BmiCalculatorTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        // ทำงานก่อนเริ่มทุกๆ Test Case: เปิด Browser และไปที่ไฟล์ bmi-calculator.html ในเครื่อง
        // ไม่ต้องระบุ path ของ chromedriver เอง เพราะ Selenium Manager (built-in ตั้งแต่ Selenium 4.6+)
        // จะดาวน์โหลดและจัดการ driver เวอร์ชันที่ตรงกับ Chrome ในเครื่องให้อัตโนมัติ
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        File htmlFile = new File("src/bmi-calculator.html");
        driver.get(htmlFile.toURI().toString());
    }

    @AfterEach
    void tearDown() {
        // ทำงานหลังจบทุกๆ Test Case: ปิด Browser และคืนทรัพยากรระบบทั้งหมด
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void tc_201_calculate_bmi_metric_units_normal_result() {
        // 1. คลิกแท็บ Metric Units
        WebElement metricTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-unit='metric']")));
        metricTab.click();

        // 2. กรอก Age
        WebElement ageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cage")));
        ageInput.clear();
        ageInput.sendKeys("25");

        // 3. เลือก Gender = Male
        WebElement maleRadio = driver.findElement(By.id("genderMale"));
        if (!maleRadio.isSelected()) {
            maleRadio.click();
        }

        // 4. กรอก Height (cm)
        WebElement heightInput = driver.findElement(By.id("cheightmeter"));
        heightInput.clear();
        heightInput.sendKeys("180");

        // 5. กรอก Weight (kg)
        WebElement weightInput = driver.findElement(By.id("ckg"));
        weightInput.clear();
        weightInput.sendKeys("65");

        // 6. คลิกปุ่ม Calculate
        WebElement calculateBtn = driver.findElement(By.id("calculateBtn"));
        calculateBtn.click();

        // 7. รอผลลัพธ์แสดงและตรวจสอบค่า BMI
        WebElement resultPanel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("bmiresult")));
        String resultText = resultPanel.getText();

        System.out.println("Result: " + resultText);

        assertTrue(resultText.contains("20.1"), "ค่า BMI ควรเป็น 20.1 kg/m2");
        assertTrue(resultText.contains("Normal"), "ผลลัพธ์ควรระบุว่าอยู่ในเกณฑ์ Normal");
    }
}
