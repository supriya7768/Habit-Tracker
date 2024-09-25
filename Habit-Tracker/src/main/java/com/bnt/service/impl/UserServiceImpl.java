package com.bnt.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bnt.constant.enums.UserResponseMessage;
import com.bnt.entity.User;
import com.bnt.exception.UserException;
import com.bnt.mapper.UserMapper;
import com.bnt.repository.UserRepository;
import com.bnt.request.UserRequest;
import com.bnt.response.UserResponse;
import com.bnt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
   }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        try {
            User user =  userRepository.save(User .builder()
            .userName(userRequest.getUserName())
            .userSurname(userRequest.getUserSurname())
            .email(userRequest.getEmail())
            .mobile(userRequest.getMobile())
            .createdAt(LocalDateTime.now())
            .build());

            return UserResponse.builder()
                   .userId(user.getUserId())
                   .userName(user.getUserName())
                   .userSurname(user.getUserSurname())
                   .email(user.getEmail())
                   .mobile(user.getMobile())
                   .createdAt(LocalDateTime.now())
                   .build();
        } catch (UserException ex) {
            throw new UserException(UserResponseMessage.FAILED_TO_SAVE_ACCOUNT.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserResponse getUser(int userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UserException(UserResponseMessage.ACCOUNT_NOT_FOUND.getMessage(),
             HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        } else 
            return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public void updateUser(UserRequest userRequest, int userId) {
       UserResponse existingUser = getUser(userId);
       User user = UserMapper.INSTANCE.userRequestToUser(userRequest);
       user.setUserId(existingUser.getUserId());
       user.setCreatedAt(LocalDateTime.now());
       userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
    } 
    
}
