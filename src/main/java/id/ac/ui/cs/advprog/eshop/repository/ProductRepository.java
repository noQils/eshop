package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        return productData.stream()
                .filter(Objects::nonNull) // ignore null elements in the list
                .filter(product -> productId.equals(product.getProductId()))
                .findFirst()
                .orElse(null);
    }

    public Product edit(String productId, Product updatedProduct) {
        return productData.stream()
                .filter(Objects::nonNull) // Ignore null elements in the list
                .filter(product -> productId.equals(product.getProductId()))
                .findFirst()
                .map(product -> {
                    product.setProductName(updatedProduct.getProductName());
                    product.setProductQuantity(updatedProduct.getProductQuantity());
                    return product;
                })
                .orElse(null);
    }

    public Product delete(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .map(product -> {
                    productData.remove(product);
                    return product;
                })
                .orElse(null);
    }
}