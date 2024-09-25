package com.bnt.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    
    private String userName;
    private String userSurname;
    private String email;
    private Long mobile;
}
