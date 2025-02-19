package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
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
    }

    @Test
    void testCreateProduct() {
        // create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c59-460e-8860-71af6af63b46");
        product.setProductName("Eb558e9f");
        product.setProductQuantity(100);

        Product result = productServiceImpl.create(product);

        // verify product exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b46", result.getProductId());
        assertEquals("Eb558e9f", result.getProductName());
        assertEquals(100, result.getProductQuantity());
    }

    @Test
    void testFindAllProducts() {
        // create a product
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c59-460e-8860-71af6af63b47");
        product1.setProductName("Laptop");
        product1.setProductQuantity(2);

        List<Product> productList = List.of(product1);
        Iterator<Product> productIterator = productList.iterator();

        // make sure findAll() returns a non-null iterator
        when(productRepository.findAll()).thenReturn(productIterator);

        // call findAll function
        List<Product> result = productServiceImpl.findAll();

        // verify created product exist
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b47", result.getFirst().getProductId());
        assertEquals("Laptop", result.getFirst().getProductName());
        assertEquals(2, result.getFirst().getProductQuantity());
    }

    @Test
    void testFindByIdProductExists() {
        // create a product
        Product product = new Product();
        product.setProductId("eb558e9f-1c59-460e-8860-71af6af63g40");
        product.setProductName("Kecap Cap Ayam");
        product.setProductQuantity(9);

        // when findById(product.getProductId()) is called, return product
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        // call the method
        Product result = productServiceImpl.findById(product.getProductId());

        // verify product exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63g40", result.getProductId());
        assertEquals("Kecap Cap Ayam", result.getProductName());
        assertEquals(9, result.getProductQuantity());
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
        existingProduct.setProductId(productId);
        existingProduct.setProductName("Old Laptop");
        existingProduct.setProductQuantity(10);
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("New Laptop");
        updatedProduct.setProductQuantity(5);

        // findById should return the existing product
        when(productRepository.findById(productId)).thenReturn(existingProduct);

        // edit should return the updated product
        when(productRepository.edit(productId, updatedProduct)).thenReturn(updatedProduct);

        // call the method
        Product result = productServiceImpl.edit(productId, updatedProduct);

        // verify the product is updated
        assertNotNull(result);
        assertEquals(updatedProduct.getProductId(), result.getProductId());
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        String productId = "eb558e9f-1c59-460e-8860-71af6af63k82";

        // create a product
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("New Laptop");
        updatedProduct.setProductQuantity(5);

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
        deletedProduct.setProductId(productId);
        deletedProduct.setProductName("Laptop");
        deletedProduct.setProductQuantity(10);

//        // delete should return the deleted product
        when(productRepository.delete(productId)).thenReturn(deletedProduct);

        // call the method
        Product result = productServiceImpl.delete(productId);

        // verify product is deleted
        assertNotNull(result);
        assertEquals(deletedProduct.getProductId(), result.getProductId());
        assertEquals(deletedProduct.getProductName(), result.getProductName());
        assertEquals(deletedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testDeleteProductNotExist() {
        Product result = productServiceImpl.delete("testing-product-id");

        // verify product does not exist
        assertNull(result);
    }
}
