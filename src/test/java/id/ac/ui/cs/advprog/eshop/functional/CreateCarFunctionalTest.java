package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateCarFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createCar_isSuccessful(ChromeDriver driver){
        // navigate to create car page
        driver.get(baseUrl + "/car/create");

        // verify the page is the create car page
        String createPageTitle = driver.getTitle();
        assertEquals("Create New Car", createPageTitle);

        // identifies the input fields
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // sending inputs to the input fields and submitting
        nameInput.sendKeys("Pagani Utopia");
        quantityInput.sendKeys("100");
        submitButton.click();

        // verify: Check if the page is the car list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/car/listCar"));

        // verify: Check if the new car appears in the list
        WebElement carTable = driver.findElement(By.cssSelector("table"));
        String tableContent = carTable.getText();
        assertTrue(tableContent.contains("Pagani Utopia"));
        assertTrue(tableContent.contains("100"));
    }

    @Test
    void createCar_emptyName_staysOnPage(ChromeDriver driver){
        // exercise: Navigate to Create Car page
        driver.get(baseUrl + "/car/create");

        // exercise: Submit form with both input fields empty
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        submitButton.click();

        // verify: Still on create car page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/car/create"));
    }

    @Test
    void createCar_thenCarAppearsInList(ChromeDriver driver){
        // navigate to "Create Car" page
        driver.get(baseUrl + "/car/create");

        // fill in the car form
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Test Car");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("10");

        // submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // ensure we are redirected to the car list page
        String currentUrl = driver.getCurrentUrl();
        assertEquals(baseUrl + "/car/listCar", currentUrl);

        // find the newly created car in the car list
        WebElement carRow = driver.findElement(By.xpath("//tr[td[text()='Test Car']]"));
        WebElement carName = carRow.findElement(By.xpath(".//td[1]"));
        WebElement carQuantity = carRow.findElement(By.xpath(".//td[2]"));

        // validate car details
        assertEquals("Test Car", carName.getText());
        assertEquals("10", carQuantity.getText());
    }
}