package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private Model model;
    @Mock
    private ProductService service;
    @InjectMocks
    ProductController productController;
    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        // verify product page opened
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        // create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c59-460e-8860-71af6af63j88");
        product.setProductName("Laptop");
        product.setProductQuantity(10);

        // call the method
        String result = productController.createProductPost(product);

        // verify page redirected to list page
        assertEquals("redirect:list", result);
    }

    @Test
    void testProductListPage() {
        // create products
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c59-460e-8860-71af6af63k23");
        product1.setProductName("Laptop");
        product1.setProductQuantity(5);
        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c59-460e-8860-71af6af63x98");
        product2.setProductName("Phone");
        product2.setProductQuantity(10);

        // call the method
        String result = productController.productListPage(model);

        // verify list page opened
        assertEquals("productList", result);
    }
}
