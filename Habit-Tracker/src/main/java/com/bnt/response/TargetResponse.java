package com.bnt.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TargetResponse {
    
    private int targetId;
    private String targetName;
    private String description;
}
