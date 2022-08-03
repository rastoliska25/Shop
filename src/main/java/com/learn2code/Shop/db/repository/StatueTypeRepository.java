package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.StatueType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatueTypeRepository extends JpaRepository<StatueType, Integer> {
}
