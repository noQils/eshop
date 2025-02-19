package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HomePageControllerTest {
    @InjectMocks
    HomePageController homePageController;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }

    @Test
    void testHomePageController() {
        String viewName = homePageController.home();

        // verify expected view name
        assertEquals("homepage", viewName);
    }
}
