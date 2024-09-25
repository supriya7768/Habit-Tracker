package com.bnt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.entity.User;
import com.bnt.request.UserRequest;
import com.bnt.response.UserResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper{
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRequestToUser(UserRequest userRequest);

    UserResponse userToUserResponse(User user);
}
