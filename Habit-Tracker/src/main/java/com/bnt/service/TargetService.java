package com.bnt.service;

import com.bnt.request.TargetRequest;
import com.bnt.response.TargetResponse;

public interface TargetService {
    
    public TargetResponse createTarget(TargetRequest targetRequest);

    public TargetResponse getTarget(int targetId);

    public void updateTarget(int targetId, TargetRequest targetRequest);

    public void deleteTarget(int targetId);
}
