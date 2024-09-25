package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import com.bnt.entity.HabitTracking;
import com.bnt.entity.Habits;

@Repository
public interface HabitTrackingRepository extends JpaRepository<HabitTracking, Integer> {

    Optional<HabitTracking> findByHabitAndDate(@Param("habit") Habits habit, @Param("date") LocalDate date);

    List<HabitTracking> findByDate(LocalDate date);

    @Query("SELECT ht FROM HabitTracking ht WHERE ht.date BETWEEN :startDate AND :endDate AND ht.habit.habitId = :habitId")
    List<HabitTracking> findHabitTrackingsWithinDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("habitId") int habitId);

    @Query("SELECT ht FROM HabitTracking ht WHERE ht.habit.habitName = :habitName " +
            "AND (:status IS NULL OR ht.statusCompletion = :status) " +
            "AND ht.date BETWEEN :startDate AND :endDate")
    List<HabitTracking> findByHabitIdAndStatusAndDateRange(
            @Param("habitName") String habitName,
            @Param("status") String status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

             @Query("SELECT DISTINCT h FROM Habits h " +
           "WHERE h.deleted = false AND " +
           "('EVERYDAY' IN (SELECT sd FROM h.selectedDays sd) OR " +
           ":dayOfWeek IN (SELECT sd FROM h.selectedDays sd)) AND " +
           "(h.selectedMonths IS EMPTY OR :month IN (SELECT sm FROM h.selectedMonths sm)) AND " +
           "(h.startDate IS NULL OR h.startDate <= CURRENT_DATE)")
    List<Habits> findHabitsForDate(@Param("dayOfWeek") String dayOfWeek,
                                   @Param("month") String month);


}
