package com.bnt.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.entity.Habits;
import com.bnt.request.HabitRequest;
import com.bnt.response.HabitResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HabitMapper {
    
    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    Habits habitRequestToHabit(HabitRequest habitRequest);

    HabitResponse habitToHabitResponse(Habits habits);

    List<HabitResponse> habitToHabitResponseList(List<Habits> habits);
}
