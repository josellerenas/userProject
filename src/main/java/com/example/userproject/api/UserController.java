package com.example.userproject.api;

import com.example.userproject.model.User;
import com.example.userproject.service.AuthService;
import com.example.userproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping
    public ResponseEntity<Long> create(@RequestHeader("Authorization") String authHeader, @RequestBody User user) {
        if (!authService.isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User newUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(newUser.getId());
    }

    @GetMapping
    public ResponseEntity<List<User>> selectAllUsers(@RequestHeader("Authorization") String authHeader) {
        if (!authService.isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<User> allUsers = userService.selectAllUsers();
        if (allUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allUsers);
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> selectUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") long id) {
        if (!authService.isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userOptional = userService.selectUser(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") long id, @RequestBody User user) {
        if (!authService.isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userOptional = userService.selectUser(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<User> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") long id) {
        if (!authService.isAuthenticated(authHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<User> userOptional = userService.selectUser(id);
        if (userOptional.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
