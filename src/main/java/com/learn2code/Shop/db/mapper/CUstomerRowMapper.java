package com.learn2code.Shop.db.mapper;

import com.learn2code.Shop.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CUstomerRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));
        customer.setAge(rs.getObject("age") == null ? null : rs.getInt("age")); //ak je v db null tak aj vráti null (keby je getInt tak vráti 0)
        customer.setPhoneNumber(rs.getString("phone_number"));

        return customer;
    }
}
