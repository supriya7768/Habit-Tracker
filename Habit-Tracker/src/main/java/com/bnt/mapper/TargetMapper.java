package com.bnt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.bnt.entity.Target;
import com.bnt.request.TargetRequest;
import com.bnt.response.TargetResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TargetMapper {
    
    TargetMapper INSTANCE = Mappers.getMapper(TargetMapper.class);

    Target targetRequestToTarget(TargetRequest targetRequest);

    TargetResponse targetToTargetResponse(Target target);
}
