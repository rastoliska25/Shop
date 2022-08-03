package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.StatueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatueTypeRepository extends JpaRepository<StatueType, Integer> {

    @Query(value = "SELECT * FROM statue_type where load_securing = 1", nativeQuery = true)
    List<StatueType> getJoinInformation();
}
