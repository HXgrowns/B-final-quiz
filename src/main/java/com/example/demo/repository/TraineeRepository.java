package com.example.demo.repository;

import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Long> {
    List<TraineeEntity> findByGroupNull();

    List<TraineeEntity> findByGroupNotNull();

    @Query(value = "update TraineeEntity t set t.group=?1")
    @Modifying
    void deleteGroup(GroupEntity group);

    @Query(value = "update trainee set group_id=?1 where id=?2", nativeQuery = true)
    @Modifying
    void updateGroupId(Long groudId, Long id);
}
