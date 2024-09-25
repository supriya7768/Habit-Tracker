package com.bnt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.constant.enums.UserResponseMessage;
import com.bnt.request.UserRequest;
import com.bnt.response.UserResponse;
import com.bnt.service.impl.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userServiceImpl.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("user-id") int userId){
        UserResponse userResponse = userServiceImpl.getUser(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(userResponse);
    }

    @PutMapping("/{user-id}")
    public ResponseEntity<String> updateUser(@PathVariable("user-id") int userId, @RequestBody UserRequest userRequest){
        userServiceImpl.updateUser(userRequest, userId);
        return ResponseEntity.ok().body(UserResponseMessage.SUCCESSFULLY_UPDATED.getMessage(userId));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-id") int userId){
        userServiceImpl.deleteUser(userId);
        return ResponseEntity.ok().body(UserResponseMessage.SUCCESSFULLY_DELETED.getMessage(userId));
    }
}
