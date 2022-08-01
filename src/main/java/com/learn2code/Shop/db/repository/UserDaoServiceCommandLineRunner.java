package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.db.service.impl.UserDAOService;
import com.learn2code.Shop.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class UserDaoServiceCommandLineRunner implements CommandLineRunner {

       private static final Logger log =
            LoggerFactory.getLogger(UserDaoServiceCommandLineRunner.class);

    @Autowired
    private UserDAOService userDAOService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Jack", "Admin");
        long insert = userDAOService.insert(user);
        log.info("New User is created : " + user);
    }
}
