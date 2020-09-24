package com.example.demo.repository;

import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TrainerRepositoryTest {
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    TestEntityManager manager;

    @Test
    void findByUserId() {
        TrainerEntity trainerEntity = TrainerEntity.builder().name("桂溪京").grouped(false).build();
        trainerEntity = manager.persistAndFlush(trainerEntity);

        List<TrainerEntity> trainerEntities = trainerRepository.findByGrouped(false);
        Assertions.assertThat(trainerEntities.get(0).getName()).isEqualTo(trainerEntity.getName());
    }
}
