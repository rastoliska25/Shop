package com.learn2code.shop.db.repository;

import com.learn2code.shop.domain.Statue;
import com.learn2code.shop.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepository extends JpaRepository<Truck, Integer> {

    public List<Truck> findByUsed(Byte used); //JPA sa postará o vytvorenie query (select * from trucks where used = x);
}
