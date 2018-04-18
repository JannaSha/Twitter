package com.user.controller;


import com.user.model.User;
import com.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;


@RestController
@RequestMapping("user")
public class UserController<T> {

    @Autowired
    private UserRepository userRepository;
    private static final Logger log = Logger.getLogger(UserController.class);

    @PostMapping(path="/createuser", produces="application/json")
    public ResponseEntity<T> createNewUser(
            @Valid @RequestBody User user,
            @RequestHeader HttpHeaders header) {
        User savedUser = userRepository.save(user);
        if (savedUser == null) {
            log.error("Error to create new user id = " + user.getId());
            throw new DataIntegrityViolationException("Error to create new user");
        }
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/user/{id}").build()
                .expand(savedUser.getId()).toUri());
        log.info("Created new user id = " + user.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path="/{id}/modify", produces = "application/json")
    public ResponseEntity<T> modifyUser(
            @PathVariable("id") long id,
            @Valid @RequestBody User user,
            @RequestHeader HttpHeaders header) {
        if (id != user.getId() || !userRepository.existsById(user.getId())) {
            log.error("Error to update user id = " + user.getId());
            throw new DataIntegrityViolationException("Error to update user");
        }
        userRepository.save(user);
        header.setLocation(ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/user/{id}").build()
                .expand(user.getId()).toUri());
        log.info("Updated user id = " + user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public User getUserById(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            log.error("Error to get user id = " + id);
            throw new EntityNotFoundException("No user by id!");
        }
        log.info("Got user id = " + id);
        return user.get();
    }

    @GetMapping(path="/all")
    public List<User> getAllUsers() {
        log.info("Got all users");
        return userRepository.findAll();
    }

    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<T> deleteUser(@PathVariable("id") long id) {
        if (userRepository.existsById(id)) {
            log.info("Deleted user id = " + id);
            userRepository.deleteById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
