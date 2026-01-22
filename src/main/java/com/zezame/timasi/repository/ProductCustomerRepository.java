package com.zezame.timasi.repository;

import com.zezame.timasi.model.company.Product;
import com.zezame.timasi.model.company.ProductCustomer;
import com.zezame.timasi.model.company.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCustomerRepository extends JpaRepository<ProductCustomer, UUID> {
    List<ProductCustomer> findByCustomerAndProductNotIn(User customer, List<Product> products);

    Optional<ProductCustomer> findByProduct(Product product);
}
