package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
