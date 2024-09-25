package com.bnt.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.bnt.constant.enums.DoAt;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int habitId;
    private String habitName;
    private Date startDate;
    private boolean reminder;
    private String value;
    private LocalTime time;
    private String trackIn;  

    private Integer hours;  
    private Integer minutes;  

    @ElementCollection(targetClass = DoAt.class)
    @CollectionTable(name = "habit_doAt", joinColumns = @JoinColumn(name = "habit_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "doAt")
    private List<DoAt> doAt;   

    @ElementCollection
    @CollectionTable(name = "habit_days", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private List<String> selectedDays;

    @ElementCollection
    @CollectionTable(name = "habit_week", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "week")
    @Enumerated(EnumType.STRING)
    private List<String> selectedWeek;

    @ElementCollection
    @CollectionTable(name = "habit_months", joinColumns = @JoinColumn(name = "habit_id"))
    @Column(name = "month")
    @Enumerated(EnumType.STRING)
    private List<String> selectedMonths;

    private LocalDateTime createdAt;

    @Builder.Default
    private boolean deleted = Boolean.FALSE;
 
}
