package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.repository.CustomerRepository;
import com.learn2code.Shop.db.service.api.CustomerService;
import com.learn2code.Shop.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //značí, že pri vytvorení aplikácie (spustení) sa vytvorí objekt CustomerServiceImpl
public class CustomerServiceImpl implements CustomerService { //service slúži na písanie logiky (napr if id > 100 then..... )

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {//mozme pouzit vdaka tomu, ze nad CustomerRepository (v triede) sme dali @Component
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public Customer get(int id) {
        return customerRepository.get(id);
    }

    @Override
    public Integer add(Customer customer) {
        return customerRepository.add(customer);
    }
}
