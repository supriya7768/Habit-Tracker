package com.bnt.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bnt.constant.enums.TargetResponseMessage;
import com.bnt.entity.Target;
import com.bnt.exception.TargetException;
import com.bnt.mapper.TargetMapper;
import com.bnt.repository.TargetRepository;
import com.bnt.request.TargetRequest;
import com.bnt.response.TargetResponse;
import com.bnt.service.TargetService;

@Service
public class TargetServiceImpl implements TargetService{

    private TargetRepository targetRepository;

    public TargetServiceImpl(TargetRepository targetRepository){
        this.targetRepository = targetRepository;
    }

    @Override
    public TargetResponse createTarget(TargetRequest targetRequest) {
       try {
        Target target = targetRepository.save(Target.builder()
        .targetName(targetRequest.getTargetName())
        .description(targetRequest.getDescription())
        .build());

        return TargetResponse.builder()
              .targetId(target.getTargetId())
              .targetName(target.getTargetName())
              .description(target.getDescription())
              .build();
       }  catch (TargetException ex) {
            throw new TargetException(TargetResponseMessage.FAILED_TO_SAVE_TARGET.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR);      
    } 
 }

    @Override
    public TargetResponse getTarget(int targetId) {
        Target target = targetRepository.findByTargetId(targetId);
         if (target == null) {
            throw new TargetException(TargetResponseMessage.TARGET_NOT_FOUND.getMessage(),
             HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
        } else 
            return TargetMapper.INSTANCE.targetToTargetResponse(target);
    }

    @Override
    public void updateTarget(int targetId, TargetRequest targetRequest) {
        TargetResponse existingTarget = getTarget(targetId);
        Target target = TargetMapper.INSTANCE.targetRequestToTarget(targetRequest);
        target.setTargetName(existingTarget.getTargetName());
        target.setDescription(existingTarget.getDescription());
        targetRepository.save(target);
    }

    @Override
    public void deleteTarget(int targetId) {
      Target target = targetRepository.findByTargetId(targetId);
      targetRepository.delete(target);
    }


}
