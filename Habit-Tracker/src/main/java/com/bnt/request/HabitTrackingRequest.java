package com.bnt.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitTrackingRequest {
    
    private int habitId;
    private String statusValue;
    private String statusTime;
    private boolean statusTick;
    private LocalDate date;
    private String comments;
    private Integer hours;  
    private Integer minutes;
    private String statusCompletion;
}
