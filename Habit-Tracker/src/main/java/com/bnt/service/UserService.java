package com.bnt.service;

import com.bnt.request.UserRequest;
import com.bnt.response.UserResponse;

public interface UserService {

    public UserResponse createUser(UserRequest userRequest);
    
    public UserResponse getUser(int userId);

    public void updateUser(UserRequest userRequest, int userId);

    public void deleteUser(int userId);

}
