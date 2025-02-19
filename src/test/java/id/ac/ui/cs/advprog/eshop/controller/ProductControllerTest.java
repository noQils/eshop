package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

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
        // This method is kept for maintainability, could be useful for future needs
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        // verify expected view name
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

        // verify expected URL
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

        // verify expected view name
        assertEquals("productList", result);
    }

    @Test
    void testEditProductPage() {
        // create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c59-460e-8860-71af6af63h88");
        product.setProductName("Laptop");
        product.setProductQuantity(5);

        // call the method
        String result = productController.editProductPage(product.getProductId(), model);

        // verify expected view name
        assertEquals("editProduct", result);
    }

    @Test
    void testEditProductPost() {
        // create products
        String productId = "eb558e9f-1c59-460e-8860-71af6af63gg3";
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setProductName("Old Laptop");
        existingProduct.setProductQuantity(10);
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("New Laptop");
        updatedProduct.setProductQuantity(5);

        // call method
        String result = productController.editProductPost(existingProduct.getProductId(), updatedProduct);

        // verify expected URL
        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testDeleteProduct() {
        String result = productController.deleteProduct("eb558e9f-1c59-460e-8860-71af6af638i2");

        // verify expected URL
        assertEquals("redirect:/product/list", result);
    }
}
