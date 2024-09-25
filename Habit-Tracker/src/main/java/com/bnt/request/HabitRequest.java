package com.bnt.request;

import com.bnt.constant.enums.Day;
import com.bnt.constant.enums.DoAt;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import java.util.Set;
import java.time.DayOfWeek;
import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitRequest {

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
}
