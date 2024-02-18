package ecom.market.inventory.service.repositories;

import ecom.market.inventory.service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
