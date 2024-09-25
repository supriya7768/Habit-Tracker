package com.bnt.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.bnt.entity.HabitTracking;
import com.bnt.entity.Habits;
import com.bnt.repository.HabitRepository;
import com.bnt.repository.HabitTrackingRepository;
import com.bnt.request.HabitTrackingRequest;
import com.bnt.response.HabitTrackingResponse;
import com.bnt.service.HabitTrackingService;

@Service
public class HabitTrackingServiceImpl implements HabitTrackingService {

    private HabitTrackingRepository habitTrackingRepository;
    private HabitRepository habitRepository;

    public HabitTrackingServiceImpl(HabitTrackingRepository habitTrackingRepository, HabitRepository habitRepository) {
        this.habitTrackingRepository = habitTrackingRepository;
        this.habitRepository = habitRepository;
    }

    @Override
    public HabitTrackingResponse createHabitTracking(HabitTrackingRequest habitTrackingRequest) {
        // Fetch the Habit entity
        Habits habit = habitRepository.findById(habitTrackingRequest.getHabitId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        // Check if HabitTracking already exists for the given habit and today's date
        LocalDate today = LocalDate.now();
        HabitTracking habitTracking = habitTrackingRepository.findByHabitAndDate(habit, today)
                .orElse(HabitTracking.builder()
                        .habit(habit)
                        .date(today)
                        .createdAt(LocalDateTime.now())
                        .build());

        // Update the existing or new HabitTracking entity
        habitTracking.setStatusValue(habitTrackingRequest.getStatusValue());
        habitTracking.setStatusTime(habitTrackingRequest.getStatusTime());
        habitTracking.setStatusTick(habitTrackingRequest.isStatusTick());
        habitTracking.setComments(habitTrackingRequest.getComments());
        habitTracking.setHours(habitTrackingRequest.getHours());
        habitTracking.setMinutes(habitTrackingRequest.getMinutes());
        habitTracking.setStatusCompletion(habitTrackingRequest.getStatusCompletion());

        // Save the HabitTracking entity
        HabitTracking savedHabitTracking = habitTrackingRepository.save(habitTracking);

        // Build and return the response
        return HabitTrackingResponse.builder()
                .trackingId(savedHabitTracking.getTrackingId())
                .habitId(savedHabitTracking.getHabit().getHabitId())
                .statusValue(savedHabitTracking.getStatusValue())
                .statusTime(savedHabitTracking.getStatusTime())
                .statusTick(savedHabitTracking.isStatusTick())
                .statusCompletion(savedHabitTracking.getStatusCompletion())
                .hours(savedHabitTracking.getHours())
                .minutes(savedHabitTracking.getMinutes())
                .date(savedHabitTracking.getDate())
                .comments(savedHabitTracking.getComments())
                .createdAt(savedHabitTracking.getCreatedAt())
                .build();
    }


    @Override
    public List<HabitTracking> getAllHabitTracking() {
       return habitTrackingRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> getHabitStats(LocalDate startDate, LocalDate endDate) {
        List<Habits> habits = habitRepository.findAll();
        List<Map<String, Object>> statsList = new ArrayList<>();
    
        for (Habits habit : habits) {
            int totalDays = calculateTotalDays(habit, startDate, endDate);
            List<HabitTracking> habitTrackings = habitTrackingRepository.findHabitTrackingsWithinDateRange(startDate, endDate, habit.getHabitId());
            
            int completedDays = (int) habitTrackings.stream()
                .filter(ht -> "Done".equalsIgnoreCase(ht.getStatusCompletion()))
                .count();
            int missedDays = totalDays - completedDays;
    
            Map<String, Object> stats = new HashMap<>();
            stats.put("habitId", habit.getHabitId());
            stats.put("habitName", habit.getHabitName());
            stats.put("totalDays", totalDays);
            stats.put("completedDays", completedDays);
            stats.put("missedDays", missedDays);
    
            statsList.add(stats);
        }
        return statsList;
    }
    
    private int calculateTotalDays(Habits habit, LocalDate startDate, LocalDate endDate) {
        int totalDays = 0;
        LocalDate currentDate = startDate;
        int weekCount = 0;
    
        while (!currentDate.isAfter(endDate)) {
            if (isHabitScheduledForDate(habit, currentDate, weekCount)) {
                totalDays++;
                weekCount++;
            }
            if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekCount = 0;
            }
            currentDate = currentDate.plusDays(1);
        }
        return totalDays;
    }
    
    private boolean isHabitScheduledForDate(Habits habit, LocalDate date, int weekCount) {
        if (habit.getSelectedDays() != null && habit.getSelectedDays().contains("EVERYDAY")) {
            return isInSelectedMonths(habit, date);
        }
    
        String dayOfWeek = date.getDayOfWeek().toString();
        if (habit.getSelectedDays() != null && habit.getSelectedDays().contains(dayOfWeek)) {
            if (habit.getSelectedWeek() != null && !habit.getSelectedWeek().isEmpty()) {
                int timesPerWeek = getWeekFrequency(habit.getSelectedWeek().get(0));
                if (isWithinAllowedWeekCount(date, weekCount, timesPerWeek)) {
                    return isInSelectedMonths(habit, date);
                } else {
                    return false;
                }
            }
            return isInSelectedMonths(habit, date);
        }
        return false;
    }
    private int getWeekFrequency(String frequency) {
        switch (frequency.toUpperCase()) {
            case "ONCE":
                return 1;
            case "TWICE":
                return 2;
            case "THRICE":
                return 3;
            case "FOURTH":
                return 4;
            default:
                return 4; // Default to "FOURTH" if unknown
        }
    }
    private boolean isWithinAllowedWeekCount(LocalDate date, int weekCount, int timesPerWeek) {
        int weekOfMonth = (date.getDayOfMonth() - 1) / 7 + 1; // Determine which week of the month
        return weekCount < timesPerWeek && weekOfMonth <= timesPerWeek;
    }
    private boolean isInSelectedMonths(Habits habit, LocalDate date) {
        if (habit.getSelectedMonths() != null && !habit.getSelectedMonths().isEmpty()) {
            return habit.getSelectedMonths().contains(date.getMonth().toString());
        }
        return true;
    }
    public List<HabitTracking> getHabitTrackings(String habitName, String status, LocalDate startDate, LocalDate endDate) {
        return habitTrackingRepository.findByHabitIdAndStatusAndDateRange(habitName, status, startDate, endDate);
    }
    
     @Override
    public List<HabitTracking> getHabitTrackingByDate(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().toString();
        int weekOfMonth = (date.getDayOfMonth() - 1) / 7 + 1;
        String month = date.getMonth().toString();
        List<Habits> relevantHabits = habitTrackingRepository.findHabitsForDate(dayOfWeek, month);
        List<HabitTracking> trackings = new ArrayList<>();
        for (Habits habit : relevantHabits) {
            if (isHabitRelevantForDate(habit, date, weekOfMonth)) {
                HabitTracking tracking = habitTrackingRepository.findByHabitAndDate(habit, date)
                    .orElse(createDefaultHabitTracking(habit, date));
                trackings.add(tracking);
            }
        }
        return trackings;
    }

    private boolean isHabitRelevantForDate(Habits habit, LocalDate date, int weekOfMonth) {
        if (habit.getStartDate() != null && date.isBefore(habit.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            return false;
        }
        if (!habit.getSelectedMonths().isEmpty() && !habit.getSelectedMonths().contains(date.getMonth().toString())) {
            return false;
        }
        if (habit.getSelectedDays().contains("EVERYDAY")) {
            return true;
        }
        if (habit.getSelectedDays().contains(date.getDayOfWeek().toString())) {
            if (habit.getSelectedWeek().isEmpty()) {
                return true;
             }
            int frequencyPerMonth = habit.getSelectedWeek().size();
            return weekOfMonth <= frequencyPerMonth;
        }
        return false;
    }
    private HabitTracking createDefaultHabitTracking(Habits habit, LocalDate date) {
        return HabitTracking.builder()
            .habit(habit)
            .date(date)
            .statusCompletion("Pending")
            .createdAt(LocalDateTime.now())
            .build();
    }

   
}
