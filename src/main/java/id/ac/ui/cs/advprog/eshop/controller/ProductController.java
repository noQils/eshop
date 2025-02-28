package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ObjectPostService;
import id.ac.ui.cs.advprog.eshop.service.ObjectGetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController extends AbstractController<Product>{

    public ProductController(ObjectGetService<Product> getService, ObjectPostService<Product> postService) {
        super(getService,
                postService,
                "CreateProduct",
                "ProductList",
                "EditProduct",
                "redirect:/product/list");
    }

    @Override
    protected Product createNewInstance() {
        return new Product();
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        return createPage(model, "product");
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product) {
        return createPost(product);
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        return listPage(model, "products");
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, Model model) {
        return editPage(productId, model, "product");
    }

    @PostMapping("/edit/{productId}")
    public String editProductPost(@PathVariable String productId, @ModelAttribute Product updatedProduct) {
        return editPost(productId, updatedProduct);
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        return delete(productId);
    }
}
