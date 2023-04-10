package com.example.userproject.service;


import com.example.userproject.model.User;
import com.example.userproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> selectUser(long id) {
        return userRepository.findById(id);
    }

    public User updateUser(long id, User user) {
        Optional<User> userToBeUpdated = userRepository.findById(id);
        return userToBeUpdated.isPresent() ? userRepository.save(new User(id, user.getName(), user.getGender())) : null;
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}