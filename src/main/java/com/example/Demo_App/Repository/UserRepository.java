package com.example.Demo_App.Repository;

import com.example.Demo_App.Models.ToDo;
import com.example.Demo_App.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
