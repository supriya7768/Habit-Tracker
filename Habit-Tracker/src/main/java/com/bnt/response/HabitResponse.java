package com.bnt.response;

import com.bnt.constant.enums.DoAt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitResponse {

    private int habitId;
    private String habitName;
    private Date startDate;
    private String value;
    private boolean reminder;
    private String trackIn;  
    private Integer hours;  
    private Integer minutes; 
    private LocalTime time;
    private List<DoAt> doAt;
    private List<String> selectedDays;  
    private List<String> selectedWeek;
    private List<String> selectedMonths; 

    private LocalDateTime createdAt;
    private Boolean deleted;

}
