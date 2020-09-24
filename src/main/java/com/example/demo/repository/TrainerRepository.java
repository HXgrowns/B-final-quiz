package com.example.demo.repository;

import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.response.GroupResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
    List<TrainerEntity> findByGroupNotNull();

    List<TrainerEntity> findByGroupNull();

    @Query(value = "update TrainerEntity t set t.group=?1")
    @Modifying
    void deleteGroup(GroupEntity group);

    @Query(value = "update trainer set group_id=?1 where id=?2", nativeQuery = true)
    @Modifying
    void updateGroupId(Long groudId, Long id);

    List<TrainerEntity> findByGroup(GroupEntity group);
}
