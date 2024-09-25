package com.bnt.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.time.LocalDate;
import java.util.List;
import com.bnt.entity.HabitTracking;
import com.bnt.request.HabitTrackingRequest;
import com.bnt.response.HabitTrackingResponse;
import com.bnt.service.impl.HabitServiceImpl;
import com.bnt.service.impl.HabitTrackingServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/habitTracking")
public class HabitTrackingController {

    HabitTrackingServiceImpl habitTrackingServiceImpl;
    HabitServiceImpl habitServiceImpl;
    
    public HabitTrackingController(HabitTrackingServiceImpl habitTrackingServiceImpl, HabitServiceImpl habitServiceImpl){
        this.habitTrackingServiceImpl = habitTrackingServiceImpl;
        this.habitServiceImpl = habitServiceImpl;
    }

    @PostMapping
    public ResponseEntity<HabitTrackingResponse> createHabitTracking(@RequestBody HabitTrackingRequest habitTrackingRequest){
        HabitTrackingResponse habitTrackingResponse = habitTrackingServiceImpl.createHabitTracking(habitTrackingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitTrackingResponse);
    }
    
    @GetMapping("/today")
   public ResponseEntity<List<HabitTracking>> getTodayHabitTracking() {
        List<HabitTracking> habitTrackings = habitServiceImpl.getTodaysHabitTracking();
        return ResponseEntity.status(HttpStatus.FOUND).body(habitTrackings);
   }

   @GetMapping()
   public ResponseEntity<List<HabitTracking>> getAllHabitTracking() {
        List<HabitTracking> habitTrackings = habitTrackingServiceImpl.getAllHabitTracking();
        return ResponseEntity.status(HttpStatus.FOUND).body(habitTrackings);
   }

    @GetMapping("/stats")
    public ResponseEntity<List<Map<String, Object>>> getHabitStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Map<String, Object>> stats = habitTrackingServiceImpl.getHabitStats(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/comments")
    public List<HabitTracking> getHabitTrackings(
            @RequestParam("habitName") String habitName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        return habitTrackingServiceImpl.getHabitTrackings(habitName, status, startDate, endDate);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<HabitTracking>> getHabitTrackingByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<HabitTracking> habitTrackings = habitTrackingServiceImpl.getHabitTrackingByDate(date);
        return ResponseEntity.ok(habitTrackings);
    }

    
    @PostMapping("/{date}")
    public HabitTracking saveHabitTrackingForDate(
            @PathVariable("date") String date,
            @RequestBody HabitTrackingRequest trackingData) {
        LocalDate customDate = LocalDate.parse(date); // Assuming the date is passed in ISO format (yyyy-MM-dd)
        trackingData.setDate(customDate); // Set the date from path variable
        return habitServiceImpl.saveHabitTrackingForDate(trackingData);
    }
}
