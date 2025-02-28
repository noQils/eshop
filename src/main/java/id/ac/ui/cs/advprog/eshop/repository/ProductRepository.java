package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends AbstractRepository<Product> {
    @Override
    protected String getId(Product product) {
        return product.getId();
    }

    @Override
    protected void setId(Product product, String id) {
        product.setId(id);
    }

    @Override
    protected void edit(Product existingProduct, Product updatedProduct) {
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setQuantity(updatedProduct.getQuantity());
    }
}