package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.service.api.UserService;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDAOService implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer get(int id) {
        return null;
    }

    public Integer insert(User user){
        entityManager.persist(user);

        return user.getId();
    }




}
