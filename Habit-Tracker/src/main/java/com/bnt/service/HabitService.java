package com.bnt.service;

import java.util.List;

import com.bnt.entity.HabitTracking;
import com.bnt.request.HabitRequest;
import com.bnt.response.HabitResponse;

public interface HabitService {

    public HabitResponse createHabit(HabitRequest habitRequest);

    public List<HabitResponse> getAllHabit();

    public void updateHabit(int habitId, HabitRequest updatedRequest);

    public void deactivateHabit(int habitId);

    public HabitResponse getHabitById(int habitId);

    public List<HabitTracking> getTodaysHabitTracking();
    
}
