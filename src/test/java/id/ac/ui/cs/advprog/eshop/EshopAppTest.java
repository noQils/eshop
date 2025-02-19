package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EshopAppTest {

    @Test
    void contextLoads() {
        // This test method is intentionally left empty. It serves as a basic smoke test
        // to ensure that the Spring context loads successfully. This can help detect
        // misconfigurations in the Spring setup. If the context fails to load, this
        // test will fail, indicating a problem with the application's configuration.
        // It doesn't assert anything specific about the application's behavior beyond
        // the successful startup.
    }

    @Test
    void mainMethodStartsSuccessfully() {
        String[] args = {};
        EshopApplication.main(args);
        assertTrue(true);
    }
}

