package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setId("eb558e9f-1c59-460e-8860-71af6af63b46");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        List<Product> productList = productRepository.findAll();
        assertTrue(productList.contains(product));
        Product savedProduct = productList.getFirst();
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.isEmpty());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63b46");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setName("Sampo Cap Useb");
        product2.setQuantity(50);
        productRepository.create(product2);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = productList.removeFirst();

        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productList.removeFirst();
        assertEquals(product2.getId(), savedProduct.getId());
        assertTrue(productList.isEmpty());
    }

    @Test
    void testEditProduct() {
        // create initial product
        Product product = new Product();
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);
        String productId = product.getId();

        // create updated product with same ID
        Product updatedProduct = new Product();
        updatedProduct.setName("Sampo Cap Asep");
        updatedProduct.setQuantity(200);

        // perform update
        Product result = productRepository.edit(productId, updatedProduct);

        // verify update
        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getQuantity(), result.getQuantity());

        // verify through findAll
        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = productList.getFirst();
        assertEquals(updatedProduct.getName(), savedProduct.getName());
        assertEquals(updatedProduct.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testEmptyProduct() {
        // create a product
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setQuantity(100);

        // verify product does not exist
        Product result = productRepository.edit("hello-world", updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        // create a product
        Product product = new Product();
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        // verify product exists
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.contains(product));

        // delete the product
        Product deletedProduct = productRepository.delete(product.getId());
        assertEquals(product.getId(), deletedProduct.getId());

        // verify product no longer exists
        productList = productRepository.findAll();
        assertFalse(productList.contains(product));
    }

    @Test
    void testDeleteNonExistentProduct() {
        // delete non-existent product
        Product deletedProduct = productRepository.delete("hello-world");

        // verify no products deleted
        assertNull(deletedProduct);

        // verify no products exist
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.isEmpty());
    }

    @Test
    void testFindProductById() {
        // create a product
        Product product = new Product();
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);

        // add product to database
        productRepository.create(product);

        // verify product exists in database
        Product dummyProduct = productRepository.findById(product.getId());
        assertEquals(product.getId(), dummyProduct.getId());
        assertEquals(product.getName(), dummyProduct.getName());
        assertEquals(product.getQuantity(), dummyProduct.getQuantity());
    }

    @Test
    void testFindNonExistenceProductById() {
        // verify function returns null given non existent id
        Product product = productRepository.findById("non-existent-id");
        assertNull(product);
    }
}