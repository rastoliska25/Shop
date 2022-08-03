package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Integer> {
}
