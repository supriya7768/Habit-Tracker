package com.bnt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.constant.enums.HabitResponseMessage;
import com.bnt.request.HabitRequest;
import com.bnt.response.HabitResponse;
import com.bnt.service.impl.HabitServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/habit")
public class HabitController {

    private HabitServiceImpl habitServiceImpl;

    public HabitController(HabitServiceImpl habitServiceImpl){
        this.habitServiceImpl = habitServiceImpl;
    }

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(@RequestBody HabitRequest habitRequest){
        HabitResponse habitResponse = habitServiceImpl.createHabit(habitRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitResponse);
    }

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getAllHabit(){
        List<HabitResponse> habitResponse = habitServiceImpl.getAllHabit();
        return ResponseEntity.status(HttpStatus.FOUND).body(habitResponse);
    }
    
    @PutMapping("/{habitId}")
    public ResponseEntity<String> updateHabit(@PathVariable("habitId") int habitId, @RequestBody HabitRequest habitRequest){
        habitServiceImpl.updateHabit(habitId, habitRequest);
        return ResponseEntity.ok().body(HabitResponseMessage.SUCCESSFULLY_UPDATED.getMessage());
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable("habitId") int habitId) {
        HabitResponse habitResponse = habitServiceImpl.getHabitById(habitId);
        return ResponseEntity.status(HttpStatus.FOUND).body(habitResponse);
    }

    @DeleteMapping("{habitId}")
    public ResponseEntity<String> deactivateHabit(@PathVariable("habitId") int habitId){
        habitServiceImpl.deactivateHabit(habitId);
        return ResponseEntity.ok().body(HabitResponseMessage.SUCCESSFULLY_DELETED.getMessage());
    }

      

}
