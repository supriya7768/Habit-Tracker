package com.bnt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bnt.constant.enums.HabitResponseMessage;
import com.bnt.entity.HabitTracking;
import com.bnt.entity.Habits;
import com.bnt.exception.HabitException;
import com.bnt.mapper.HabitMapper;
import com.bnt.repository.HabitRepository;
import com.bnt.repository.HabitTrackingRepository;
import com.bnt.request.HabitRequest;
import com.bnt.request.HabitTrackingRequest;
import com.bnt.response.HabitResponse;
import com.bnt.service.HabitService;

import java.time.LocalDate;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final HabitTrackingRepository habitTrackingRepository;

    public HabitServiceImpl(HabitRepository habitRepository, HabitTrackingRepository habitTrackingRepository) {
        this.habitRepository = habitRepository;
        this.habitTrackingRepository = habitTrackingRepository;
    }

    @Override
    public HabitResponse createHabit(HabitRequest habitRequest) {
        // Default all months if no months are selected
        List<String> months = habitRequest.getSelectedMonths();
        if (months == null || months.isEmpty()) {
            months = List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                    "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
        }

        // Check if trackIn is null or empty
        if (habitRequest.getTrackIn() != null && !habitRequest.getTrackIn().isEmpty()) {
            // Build the habit entity
            Habits.HabitsBuilder habitBuilder = Habits.builder()
                    .habitName(habitRequest.getHabitName())
                    .startDate(habitRequest.getStartDate())
                    .doAt(habitRequest.getDoAt())
                    .reminder(habitRequest.isReminder())
                    .trackIn(habitRequest.getTrackIn())
                    .time(habitRequest.getTime())
                    .selectedDays(habitRequest.getSelectedDays())
                    .selectedWeek(habitRequest.getSelectedWeek())
                    .selectedMonths(months)
                    .createdAt(LocalDateTime.now());

            // Handle DURATION case or other cases
            if ("DURATION".equalsIgnoreCase(habitRequest.getTrackIn())) {
                habitBuilder.hours(habitRequest.getHours());
                habitBuilder.minutes(habitRequest.getMinutes());
                habitBuilder.value(null);
            } else {
                habitBuilder.value(habitRequest.getValue());
                habitBuilder.hours(null);
                habitBuilder.minutes(null);
            }

            Habits habit = habitBuilder.build();
            Habits savedHabit = habitRepository.save(habit);

            // Return the response object
            return HabitResponse.builder()
                    .habitId(savedHabit.getHabitId())
                    .habitName(savedHabit.getHabitName())
                    .startDate(savedHabit.getStartDate())
                    .doAt(savedHabit.getDoAt())
                    .time(savedHabit.getTime())
                    .trackIn(savedHabit.getTrackIn())
                    .reminder(savedHabit.isReminder())
                    .value(savedHabit.getValue())
                    .hours(savedHabit.getHours())
                    .minutes(savedHabit.getMinutes())
                    .selectedDays(savedHabit.getSelectedDays())
                    .selectedWeek(savedHabit.getSelectedWeek())
                    .selectedMonths(savedHabit.getSelectedMonths())
                    .createdAt(savedHabit.getCreatedAt())
                    .deleted(savedHabit.isDeleted())
                    .build();
        } else {
            // If trackIn is null or empty, handle it appropriately
            Habits.HabitsBuilder habitBuilder = Habits.builder()
                    .habitName(habitRequest.getHabitName())
                    .startDate(habitRequest.getStartDate())
                    .doAt(habitRequest.getDoAt())
                    .reminder(habitRequest.isReminder())
                    .time(habitRequest.getTime())
                    .selectedDays(habitRequest.getSelectedDays())
                    .selectedWeek(habitRequest.getSelectedWeek())
                    .selectedMonths(months)
                    .createdAt(LocalDateTime.now());

            Habits habit = habitBuilder.build();
            Habits savedHabit = habitRepository.save(habit);

            return HabitResponse.builder()
                    .habitId(savedHabit.getHabitId())
                    .habitName(savedHabit.getHabitName())
                    .startDate(savedHabit.getStartDate())
                    .doAt(savedHabit.getDoAt())
                    .time(savedHabit.getTime())
                    .trackIn(null) // Explicitly set as null if not provided
                    .reminder(savedHabit.isReminder())
                    .value(null)
                    .hours(null)
                    .minutes(null)
                    .selectedDays(savedHabit.getSelectedDays())
                    .selectedWeek(savedHabit.getSelectedWeek())
                    .selectedMonths(savedHabit.getSelectedMonths())
                    .createdAt(savedHabit.getCreatedAt())
                    .deleted(savedHabit.isDeleted())
                    .build();
        }
    }

    @Override
    public List<HabitResponse> getAllHabit() {
        List<Habits> habits = habitRepository.findAll();
        // Filter out habits marked as deleted
        List<Habits> activeHabits = habits.stream()
                .filter(habit -> !habit.isDeleted())
                .collect(Collectors.toList());
        return HabitMapper.INSTANCE.habitToHabitResponseList(activeHabits);
    }

    @Override
    public void updateHabit(int habitId, HabitRequest updatedRequest) {
        Habits existingHabit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
    
        if (updatedRequest.getHabitName() != null) existingHabit.setHabitName(updatedRequest.getHabitName());
        if (updatedRequest.getDoAt() != null) existingHabit.setDoAt(updatedRequest.getDoAt());
        if (updatedRequest.getTime() != null) existingHabit.setTime(updatedRequest.getTime());
        if (updatedRequest.getTrackIn() != null) existingHabit.setTrackIn(updatedRequest.getTrackIn());
        existingHabit.setReminder(updatedRequest.isReminder());
        if (updatedRequest.getStartDate() != null) existingHabit.setStartDate(updatedRequest.getStartDate());
        
        if ("Duration".equalsIgnoreCase(updatedRequest.getTrackIn())) {
            if (updatedRequest.getHours() != null) existingHabit.setHours(updatedRequest.getHours());
            if (updatedRequest.getMinutes() != null) existingHabit.setMinutes(updatedRequest.getMinutes());
            existingHabit.setValue(null);
        } else {
            if (updatedRequest.getValue() != null) existingHabit.setValue(updatedRequest.getValue());
            existingHabit.setHours(null);
            existingHabit.setMinutes(null);
        }
    
        if (updatedRequest.getSelectedDays() != null) existingHabit.setSelectedDays(updatedRequest.getSelectedDays());
        if (updatedRequest.getSelectedWeek() != null) existingHabit.setSelectedWeek(updatedRequest.getSelectedWeek());
        if (updatedRequest.getSelectedMonths() != null) existingHabit.setSelectedMonths(updatedRequest.getSelectedMonths());
    
        habitRepository.save(existingHabit);
    }

    @Transactional
    @Override
    public void deactivateHabit(int habitId) {
        Habits habit = habitRepository.findByHabitId(habitId);
        if (habit == null) {
            throw new HabitException(HabitResponseMessage.HABIT_NOT_FOUND.getMessage(habitId),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        }
        habitRepository.softDeleteByHabitId(habitId);
    }

    @Override
    public HabitResponse getHabitById(int habitId) {
        Habits habit = habitRepository.findByHabitId(habitId);
        return HabitMapper.INSTANCE.habitToHabitResponse(habit);
    }

    @Override
    public List<HabitTracking> getTodaysHabitTracking() {
        LocalDate today = LocalDate.now();
        String dayOfWeek = today.getDayOfWeek().name();
        String weekOfMonth = "WEEK_" + ((today.getDayOfMonth() - 1) / 7 + 1);
        String month = today.getMonth().name();

        List<Habits> relevantHabits = habitRepository.findHabitsForDate(dayOfWeek, weekOfMonth, month);

        return relevantHabits.stream()
                .map(habit -> habitTrackingRepository.findByHabitAndDate(habit, today)
                        .orElse(HabitTracking.builder()
                                .habit(habit)
                                .date(today)
                                .createdAt(LocalDateTime.now())
                                .build()))
                .collect(Collectors.toList());
    }

    @Transactional
    public HabitTracking saveHabitTrackingForDate(HabitTrackingRequest trackingData) {
        // Retrieve the Habit entity using the habitId from the request
        Habits habit = habitRepository.findById(trackingData.getHabitId())
                .orElseThrow(() -> new RuntimeException("Habit not found"));

        // Find existing tracking for the given habit and date
        HabitTracking existingTracking = habitTrackingRepository.findByHabitAndDate(habit, trackingData.getDate())
                .orElse(HabitTracking.builder()
                        .habit(habit)
                        .date(trackingData.getDate())
                        .createdAt(LocalDateTime.now())
                        .build());

        // Update the tracking data
        existingTracking.setStatusValue(trackingData.getStatusValue());
        existingTracking.setStatusTime(trackingData.getStatusTime());
        existingTracking.setHours(trackingData.getHours());
        existingTracking.setMinutes(trackingData.getMinutes());
        existingTracking.setStatusTick(trackingData.isStatusTick());
        existingTracking.setStatusCompletion(trackingData.getStatusCompletion());
        existingTracking.setComments(trackingData.getComments());

        // Save and return the updated tracking
        return habitTrackingRepository.save(existingTracking);
    }

}
