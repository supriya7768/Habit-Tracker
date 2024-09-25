package com.bnt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.constant.enums.TargetResponseMessage;
import com.bnt.request.TargetRequest;
import com.bnt.response.TargetResponse;
import com.bnt.service.impl.TargetServiceImpl;

@RestController
@RequestMapping("/api/target")
public class TargetController {

    private TargetServiceImpl targetServiceImpl;

    public TargetController(TargetServiceImpl targetServiceImpl){
        this.targetServiceImpl = targetServiceImpl;
    }

    @PostMapping
    public ResponseEntity<TargetResponse> createTarget(@RequestBody TargetRequest targetRequest){
        TargetResponse targetResponse = targetServiceImpl.createTarget(targetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(targetResponse);
    }

    @GetMapping("/{target-id}")
    public ResponseEntity<TargetResponse> getTarget(@PathVariable("target-id") int targetId){
        TargetResponse targetResponse = targetServiceImpl.getTarget(targetId);
        return ResponseEntity.status(HttpStatus.FOUND).body(targetResponse);
    }

    @PutMapping("/{target-id}")
    public ResponseEntity<String> updateTarget(@PathVariable("target-id") int targetId, @RequestBody TargetRequest targetRequest){
        targetServiceImpl.updateTarget(targetId, targetRequest);
        return ResponseEntity.ok().body(TargetResponseMessage.SUCCESSFULLY_UPDATED.getMessage(targetId));
    }

    @DeleteMapping("/{target-id}")
    public ResponseEntity<String> deleteTarget(@PathVariable("target-id") int targetId){
        targetServiceImpl.deleteTarget(targetId);
        return ResponseEntity.ok().body(TargetResponseMessage.SUCCESSFULLY_DELETED.getMessage(targetId));
    }
    
}
