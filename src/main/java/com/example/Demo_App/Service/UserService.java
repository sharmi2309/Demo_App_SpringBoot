package com.example.Demo_App.Service;

import com.example.Demo_App.Models.User;
import com.example.Demo_App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User createUser(User user)
    {
        return userRepository.save(user);
    }
    public User getById(Long id)
    {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("Data not Found"));
    }
}
