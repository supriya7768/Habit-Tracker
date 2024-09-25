package com.bnt.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

    private int userId;
    private String userName;
    private String userSurname;
    private String email;
    private Long mobile;
    private LocalDateTime createdAt;
    
}
