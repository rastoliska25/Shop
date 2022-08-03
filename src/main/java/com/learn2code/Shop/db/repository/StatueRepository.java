package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Statue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatueRepository extends JpaRepository<Statue, Integer> {
}
