package com.example.demo.entity;

import com.example.demo.dto.Trainee;
import com.example.demo.dto.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "group", cascade=CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TrainerEntity> trainers;
    @OneToMany(mappedBy = "group", cascade=CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TraineeEntity> trainees;
}
