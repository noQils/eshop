package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c59-460e-8860-71af6af63b46");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63b46");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Useb");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        // create initial product
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        String productId = product.getProductId();

        // create updated product with same ID
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Cap Asep");
        updatedProduct.setProductQuantity(200);

        // perform update
        Product result = productRepository.edit(productId, updatedProduct);

        // verify update
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());

        // verify through findAll
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEmptyProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(100);

        Product result = productRepository.edit("hello-world", updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        // create a product
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // verify product exists
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        // delete the product
        Product deletedProduct = productRepository.delete(product.getProductId());
        assertEquals(product.getProductId(), deletedProduct.getProductId());

        // verify product no longer exists
        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        // delete non-existent product
        Product deletedProduct = productRepository.delete("hello-world");

        // verify no products deleted
        assertNull(deletedProduct);

        // verify no products exist
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product dummyProduct = productRepository.findById(product.getProductId());
        assertEquals(product.getProductId(), dummyProduct.getProductId());
        assertEquals(product.getProductName(), dummyProduct.getProductName());
        assertEquals(product.getProductQuantity(), dummyProduct.getProductQuantity());
    }

    @Test
    void testFindNonExistenceProductById() {
        Product product = productRepository.findById("non-existent-id");
        assertNull(product);
    }

    @Test
    void testFindProductByIdWithNullId() {
        Product product = productRepository.findById(null);
        assertNull(product);
    }

    @Test
    void testEditExistingProduct() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.edit("123", updatedProduct);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.edit("non-existent-id", updatedProduct);
        assertNull(result);
    }

    @Test
    void testEditWithNullId() {
        Product result1 = productRepository.edit(null, null);
        assertNull(result1);

        Product result2 = productRepository.edit("test", null);
        assertNull(result2);

        Product updateProduct = new Product();
        updateProduct.setProductName("New Name");
        updateProduct.setProductQuantity(20);
        Product result3 = productRepository.edit(null, updateProduct);
        assertNull(result3);
    }
}