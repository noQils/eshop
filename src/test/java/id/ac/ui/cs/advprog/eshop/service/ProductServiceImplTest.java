package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productServiceImpl;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }

    @Test
    void testCreateProduct() {
        // create a product
        Product product = new Product();
        product.setId("eb558e9f-1c59-460e-8860-71af6af63b46");
        product.setName("Eb558e9f");
        product.setQuantity(100);

        // make sure findAll() returns a non-null iterator
        when(productRepository.create(product)).thenReturn(product);

        Product result = productServiceImpl.create(product);

        // verify product exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b46", result.getId());
        assertEquals("Eb558e9f", result.getName());
        assertEquals(100, result.getQuantity());
    }

    @Test
    void testFindAllProducts() {
        // create a product
        Product product1 = new Product();
        product1.setId("eb558e9f-1c59-460e-8860-71af6af63b47");
        product1.setName("Laptop");
        product1.setQuantity(2);

        List<Product> productList = List.of(product1);

        // make sure findAll() returns a non-null iterator
        when(productRepository.findAll()).thenReturn(productList);

        // call findAll function
        List<Product> result = productServiceImpl.findAll();

        // verify created product exist
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b47", result.getFirst().getId());
        assertEquals("Laptop", result.getFirst().getName());
        assertEquals(2, result.getFirst().getQuantity());
    }

    @Test
    void testFindByIdProductExists() {
        // create a product
        Product product = new Product();
        product.setId("eb558e9f-1c59-460e-8860-71af6af63g40");
        product.setName("Kecap Cap Ayam");
        product.setQuantity(9);

        // when findById(product.getId()) is called, return product
        when(productRepository.findById(product.getId())).thenReturn(product);

        // call the method
        Product result = productServiceImpl.findById(product.getId());

        // verify product exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63g40", result.getId());
        assertEquals("Kecap Cap Ayam", result.getName());
        assertEquals(9, result.getQuantity());
    }

    @Test
    void testFindByIdProductNotExists() {
        // when findById("999") is called, return null
        when(productRepository.findById("999")).thenReturn(null);

        // call the method
        Product result = productServiceImpl.findById("999");

        // verify product does not exist
        assertNull(result);
    }

    @Test
    void testEditProductExists() {
        String productId = "eb558e9f-1c59-460e-8860-71af6af63g45";
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Laptop");
        existingProduct.setQuantity(10);
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("New Laptop");
        updatedProduct.setQuantity(5);

        // edit should return the updated product
        when(productRepository.edit(productId, updatedProduct)).thenReturn(updatedProduct);

        // call the method
        Product result = productServiceImpl.edit(productId, updatedProduct);

        // verify the product is updated
        assertNotNull(result);
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getQuantity(), result.getQuantity());
    }

    @Test
    void testEditProductNotFound() {
        String productId = "eb558e9f-1c59-460e-8860-71af6af63k82";

        // create a product
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("New Laptop");
        updatedProduct.setQuantity(5);

        // call the method
        Product result = productServiceImpl.edit(productId, updatedProduct);

        // verify method returns null (product does not exist)
        assertNull(result);
    }

    @Test
    void testDeleteProductExists() {
        String productId = "eb558e9f-1c59-460e-8860-71af6af63g45";

        // create a product
        Product deletedProduct = new Product();
        deletedProduct.setId(productId);
        deletedProduct.setName("Laptop");
        deletedProduct.setQuantity(10);

        // delete should return the deleted product
        when(productRepository.delete(productId)).thenReturn(deletedProduct);

        // call the method
        Product result = productServiceImpl.delete(productId);

        // verify product is deleted
        assertNotNull(result);
        assertEquals(deletedProduct.getId(), result.getId());
        assertEquals(deletedProduct.getName(), result.getName());
        assertEquals(deletedProduct.getQuantity(), result.getQuantity());
    }

    @Test
    void testDeleteProductNotExist() {
        Product result = productServiceImpl.delete("testing-product-id");

        // verify product does not exist
        assertNull(result);
    }
}
