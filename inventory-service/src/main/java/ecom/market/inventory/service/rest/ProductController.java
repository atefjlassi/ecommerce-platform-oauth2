package ecom.market.inventory.service.rest;

import ecom.market.inventory.service.entities.Product;
import ecom.market.inventory.service.repositories.ProductRepository;
import jakarta.ws.rs.Path;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Product> findAllProducts() {
       return this.productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product findProductById(@PathVariable("id") String id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }

        throw new NoSuchElementException("No element exist with id "+id);
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }
}
