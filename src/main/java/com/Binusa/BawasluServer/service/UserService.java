package com.Binusa.BawasluServer.service;

import com.Binusa.BawasluServer.model.UserModel;
import com.Binusa.BawasluServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// UserService.java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
