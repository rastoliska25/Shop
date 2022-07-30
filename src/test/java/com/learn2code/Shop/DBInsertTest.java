package com.learn2code.Shop;


import com.learn2code.Shop.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootTest
public class DBInsertTest {

    private final String insertCustomer = "INSERT INTO customer(name, surname, email, address, age, phone_number) values (?, ?, ?, ?, ?, ?)";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setName("Ferko");
        customer.setSurname("Mrkvicka");
        customer.setEmail("xxx");
        customer.setAddress("xxx");
        customer.setAge(17);
        customer.setPhoneNumber("xxx");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertCustomer);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                ps.setInt(5, customer.getAge());
                ps.setString(6, customer.getPhoneNumber());
                return ps;
            }
        });
    }
}
