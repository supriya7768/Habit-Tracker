package com.bnt.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int targetId;
    private String targetName;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "user_target",
        joinColumns = @JoinColumn(name = "target_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;
}
