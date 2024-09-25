package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bnt.entity.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {
    
     @Query(value = "SELECT * FROM target WHERE target_id = ?", nativeQuery = true)
    public Target findByTargetId(int targetId);
}
