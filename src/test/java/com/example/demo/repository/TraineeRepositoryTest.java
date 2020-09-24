package com.example.demo.repository;

import com.example.demo.entity.TraineeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TraineeRepositoryTest {
    @Autowired
    TraineeRepository traineeRepository;
    @Autowired
    TestEntityManager manager;

    @Test
    void findByUserId() {
        TraineeEntity traineeEntity = TraineeEntity.builder().name("沈乐棋").build();
        traineeEntity = manager.persistAndFlush(traineeEntity);

        List<TraineeEntity> traineeEntities = traineeRepository.findByGroupNotNull();
        Assertions.assertThat(traineeEntities.get(0).getName()).isEqualTo(traineeEntity.getName());
    }
}
