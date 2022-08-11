package com.learn2code.shop.db.repository;

import com.learn2code.shop.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Integer> {
}
