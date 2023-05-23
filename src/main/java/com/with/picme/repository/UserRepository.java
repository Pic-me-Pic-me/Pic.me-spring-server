package com.with.picme.repository;

import com.with.picme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean findByUserName(String userName);
    boolean findByEmail(String email);
}
