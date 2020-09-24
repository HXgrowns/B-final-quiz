package com.example.demo.repository;

import com.example.demo.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Long> {
    List<TraineeEntity> findByGrouped(Boolean grouped);
}
