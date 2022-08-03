package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.StatueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatueRepository extends JpaRepository<Statue, Integer> {

    @Query(value = "SELECT * FROM statues where name = :name", nativeQuery = true)
    List<Statue> getJoinInformation(@Param("name") String firstName);
}
