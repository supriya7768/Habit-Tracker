package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import com.bnt.entity.Habits;

@Repository
public interface HabitRepository extends JpaRepository<Habits, Integer> {

    @Query(value = "SELECT * FROM habits WHERE habit_id = ?", nativeQuery = true)
    public Habits findByHabitId(int habitId);

    @Modifying
    @Query(value = "UPDATE habits SET deleted = true WHERE habit_id = ?", nativeQuery = true)
    public void softDeleteByHabitId(int habitId);


//     @Query("SELECT DISTINCT h FROM Habits h " +
//             "WHERE h.deleted = false AND " +
//             "(" +
//             "   'EVERYDAY' IN (SELECT sd FROM h.selectedDays sd) OR " +
//             "   :dayOfWeek IN (SELECT sd FROM h.selectedDays sd) OR " +
//             "   (:weekOfMonth IN (SELECT sw FROM h.selectedWeek sw) AND " +
//             "    (h.selectedDays IS EMPTY OR :dayOfWeek IN (SELECT sd FROM h.selectedDays sd))) OR " +
//             "   (:month IN (SELECT sm FROM h.selectedMonths sm) AND " +
//             "    (h.selectedDays IS EMPTY OR :dayOfWeek IN (SELECT sd FROM h.selectedDays sd)) AND " +
//             "    (h.selectedWeek IS EMPTY OR :weekOfMonth IN (SELECT sw FROM h.selectedWeek sw)))" +
//             ") AND " +
//             "(" +
//             "   h.selectedMonths IS EMPTY OR " +
//             "   :month IN (SELECT sm FROM h.selectedMonths sm)" +
//             ")")
//     List<Habits> findHabitsForDate(@Param("dayOfWeek") String dayOfWeek,
//             @Param("weekOfMonth") String weekOfMonth,
//             @Param("month") String month);

            @Query("SELECT DISTINCT h FROM Habits h " +
       "WHERE h.deleted = false AND " +
       "(" +
       "   'EVERYDAY' IN (SELECT sd FROM h.selectedDays sd) OR " +
       "   :dayOfWeek IN (SELECT sd FROM h.selectedDays sd) OR " +
       "   (:weekOfMonth IN (SELECT sw FROM h.selectedWeek sw) AND " +
       "    (h.selectedDays IS EMPTY OR :dayOfWeek IN (SELECT sd FROM h.selectedDays sd))) OR " +
       "   (:month IN (SELECT sm FROM h.selectedMonths sm) AND " +
       "    (h.selectedDays IS EMPTY OR :dayOfWeek IN (SELECT sd FROM h.selectedDays sd)) AND " +
       "    (h.selectedWeek IS EMPTY OR :weekOfMonth IN (SELECT sw FROM h.selectedWeek sw)))" +
       ") AND " +
       "(" +
       "   h.selectedMonths IS EMPTY OR " +
       "   :month IN (SELECT sm FROM h.selectedMonths sm)" +
       ")")
List<Habits> findHabitsForDate(@Param("dayOfWeek") String dayOfWeek, 
                               @Param("weekOfMonth") String weekOfMonth, 
                               @Param("month") String month);

            
}
