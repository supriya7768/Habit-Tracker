package com.bnt.service;

import java.util.List;
import java.time.LocalDate;

import java.util.Map;

import com.bnt.entity.HabitTracking;
import com.bnt.request.HabitTrackingRequest;
import com.bnt.response.HabitTrackingResponse;

public interface HabitTrackingService {
    
    public HabitTrackingResponse createHabitTracking(HabitTrackingRequest habitTrackingRequest);

    public List<HabitTracking> getAllHabitTracking();
    
    public List<Map<String, Object>> getHabitStats(LocalDate startDate, LocalDate endDate);

    public List<HabitTracking> getHabitTrackings(String habitName, String status, LocalDate startDate, LocalDate endDate) ;

    public List<HabitTracking> getHabitTrackingByDate(LocalDate date);
}
