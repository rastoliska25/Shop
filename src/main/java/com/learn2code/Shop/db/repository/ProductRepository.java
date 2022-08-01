package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
