package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.db.mapper.CUstomerRowMapper;
import com.learn2code.Shop.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component  //beana, aby sme mohli použiť objekt/komponent v services
public class CustomerRepository { //repository je najspodnejšia vrstva, ktorá komunikuje s DB pomocou jdbc template, nestará sa inak o logiku - čisto len komunikácia s DB
    private final JdbcTemplate jdbcTemplate;
    private final CUstomerRowMapper cUstomerRowMapper = new CUstomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer get(int id) {
        final String sql = "select * from customer where customer.id = " + id;
        try {
            return (Customer) jdbcTemplate.queryForObject(sql, cUstomerRowMapper);
        } catch (EmptyResultDataAccessException e) {//exception ked nic nevrati lebo nenajde id
            return null; //tak vrati null
        }
    }

    public Integer add(Customer customer) {
        final String sql = "insert into customer(name,surname,email,address,age,phone_number) values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); //vráti vygenerovaný klúč
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); //return generated keys -> objekt nám po setnutí v db vráti vygednerované id
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                if (customer.getAddress() != null) {
                    ps.setInt(5, customer.getAge()); //ak nieje null, setne cislo ,lebo setInt nesmie byt null
                } else {
                    ps.setNull(5, Types.INTEGER); //ak je null, setne null
                }
                ps.setString(6,customer.getPhoneNumber());
                return ps;
            }
        }, keyHolder);

        if(keyHolder.getKey() != null) { //pomocou keyholdera vieme získať vygenerované id (nové id v tabulke v db) - niečo ako aktualne maxID
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Customer> getAll() { //vrátenie všetkých zákazníkov
        final String sql = "select * from customer";
        return jdbcTemplate.query(sql, cUstomerRowMapper);
         }
}
