package com.with.picme.repository;

import com.with.picme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByName(String userName);

    boolean existsUserByEmail(String email);

    User findByEmail(String email);
}
