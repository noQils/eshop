package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ObjectGetService;
import id.ac.ui.cs.advprog.eshop.service.ObjectPostService;
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
    private ObjectGetService getService;
    @Mock
    private ObjectPostService postService;
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
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        // create a product
        Product product = new Product();
        product.setId("eb558e9f-1c59-460e-8860-71af6af63j88");
        product.setName("Laptop");
        product.setQuantity(10);

        // call the method
        String result = productController.createProductPost(product);

        // verify expected URL
        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testProductListPage() {
        // create products
        Product product1 = new Product();
        product1.setId("eb558e9f-1c59-460e-8860-71af6af63k23");
        product1.setName("Laptop");
        product1.setQuantity(5);
        Product product2 = new Product();
        product2.setId("eb558e9f-1c59-460e-8860-71af6af63x98");
        product2.setName("Phone");
        product2.setQuantity(10);

        // call the method
        String result = productController.productListPage(model);

        // verify expected view name
        assertEquals("ProductList", result);
    }

    @Test
    void testEditProductPage() {
        // create a product
        Product product = new Product();
        product.setId("eb558e9f-1c59-460e-8860-71af6af63h88");
        product.setName("Laptop");
        product.setQuantity(5);

        // call the method
        String result = productController.editProductPage(product.getId(), model);

        // verify expected view name
        assertEquals("EditProduct", result);
    }

    @Test
    void testEditProductPost() {
        // create products
        String productId = "eb558e9f-1c59-460e-8860-71af6af63gg3";
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Laptop");
        existingProduct.setQuantity(10);
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("New Laptop");
        updatedProduct.setQuantity(5);

        // call method
        String result = productController.editProductPost(existingProduct.getId(), updatedProduct);

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
