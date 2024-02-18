package ecom.market.inventory.service;

import ecom.market.inventory.service.entities.Product;
import ecom.market.inventory.service.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository) {
        return args -> {
            repository.save(Product.builder().name("Computer").id(UUID.randomUUID().toString())
                                .price(7400).quantity(12).build());
            repository.save(Product.builder().name("Printer").id(UUID.randomUUID().toString())
                                .price(3000).quantity(5).build());
            repository.save(Product.builder().name("Smart phone").id(UUID.randomUUID().toString())
                                .price(2100).quantity(7).build());
        };
    }
}
