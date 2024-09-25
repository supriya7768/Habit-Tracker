package com.bnt.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitTrackingResponse {

    private int trackingId;
    private int habitId;
    private String statusValue;
    private String statusTime;
    private boolean statusTick;
    private String statusCompletion;
    private LocalDate date;
    private String comments;
    private Integer hours;  
    private Integer minutes;
    private LocalDateTime createdAt;
}
