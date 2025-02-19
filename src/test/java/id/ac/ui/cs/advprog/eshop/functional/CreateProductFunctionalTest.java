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
class CreateProductFunctionalTest {

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
    void createProduct_isSuccessful(ChromeDriver driver){
        // navigate to create product page
        driver.get(baseUrl + "/product/create");

        // verify the page is the create product page
        String createPageTitle = driver.getTitle();
        assertEquals("Create New Product", createPageTitle);

        // identifies the input fields
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // sending inputs to the input fields and submitting
        nameInput.sendKeys("Sampo Cap Bambang");
        quantityInput.sendKeys("100");
        submitButton.click();

        // verify: Check if the page is the product list page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/list"));

        // verify: Check if the new product appears in the list
        WebElement productTable = driver.findElement(By.cssSelector("table"));
        String tableContent = productTable.getText();
        assertTrue(tableContent.contains("Sampo Cap Bambang"));
        assertTrue(tableContent.contains("100"));
    }

    @Test
    void createProduct_emptyName_staysOnPage(ChromeDriver driver){
        // exercise: Navigate to Create Product page
        driver.get(baseUrl + "/product/create");

        // exercise: Submit form with both input fields empty
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        submitButton.click();

        // verify: Still on create product page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"));
    }

    @Test
    void createProduct_thenProductAppearsInList(ChromeDriver driver){
        // navigate to "Create Product" page
        driver.get(baseUrl + "/product/create");

        // fill in the product form
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Test Product");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("10");

        // submit the form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // ensure we are redirected to the product list page
        String currentUrl = driver.getCurrentUrl();
        assertEquals(baseUrl + "/product/list", currentUrl);

        // find the newly created product in the product list
        WebElement productRow = driver.findElement(By.xpath("//tr[td[text()='Test Product']]"));
        WebElement productName = productRow.findElement(By.xpath(".//td[1]"));
        WebElement productQuantity = productRow.findElement(By.xpath(".//td[2]"));

        // validate product details
        assertEquals("Test Product", productName.getText());
        assertEquals("10", productQuantity.getText());
    }
}