package com.example.demo.repository;

import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    List<TrainerEntity> findByGrouped(Boolean grouped);
}
