package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.User;
import org.springframework.lang.Nullable;

public interface UserService {


    @Nullable
    Customer get(int id);

    @Nullable
    Integer insert(User user); //returns generated id

}
