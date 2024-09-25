package com.bnt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitTracking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trackingId;
    private String statusValue;
    private String statusTime;
    private boolean statusTick;

    @Builder.Default
    private String statusCompletion = "Pending";
    
    private LocalDate date;
    private String comments;
    private LocalDateTime createdAt;

    private Integer hours;  
    private Integer minutes;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)  
    private Habits habit;  
}
