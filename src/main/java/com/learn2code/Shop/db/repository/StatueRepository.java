package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.Statue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatueRepository extends JpaRepository<Statue, Integer> {

    @Query(value = "SELECT * FROM statues where name = :name", nativeQuery = true)
    List<Statue> getInformation(@Param("name") String firstName);

    @Query(value = "select * from statues s LEFT JOIN statue_type st ON st.id = s.type_id where st.load_securing = :loadSecuring", nativeQuery = true)
    public List<Statue> getJoinInformation(@Param("loadSecuring") Integer loadSecuring);


    public List<Statue> findByName(String name); //JPA sa postará o vytvorenie query (select * from statues where name like "xy");
}
