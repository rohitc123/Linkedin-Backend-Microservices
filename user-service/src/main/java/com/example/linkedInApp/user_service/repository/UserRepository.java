package com.example.linkedInApp.user_service.repository;

import com.example.linkedInApp.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    Optional<Object> findByEmail(String email);
}
