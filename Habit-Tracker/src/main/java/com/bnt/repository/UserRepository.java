package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bnt.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
     @Query(value = "SELECT * FROM app_user WHERE user_id = ?", nativeQuery = true)
    public User findByUserId(int userId);
}
