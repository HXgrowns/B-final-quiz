package com.example.demo.service;

import com.example.demo.dto.Trainer;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.response.TrainerResponse;
import com.example.demo.utils.Convert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        trainerService = new TrainerService(trainerRepository);
    }
    @Test
    void should_return_trainers_by_grouped() {
        //List<TrainerEntity> trainerEntities = Arrays.asList(TrainerEntity.builder().id(1L).name("张三").build(), TrainerEntity.builder().id(2L).name("李四").build());
        //when(trainerRepository.findByGroupNotNull()).thenReturn(null);
        //when(trainerRepository.findByGroupNull()).thenReturn(trainerEntities);
        //
        //List<TrainerResponse> result = trainerService.findNotGroup(false);
        //Assertions.assertThat(trainerEntities.get(0).getName()).isEqualTo(result.get(0).getName());
        //Assertions.assertThat(trainerEntities.get(1).getName()).isEqualTo(result.get(1).getName());
    }

    @Test
    void should_create_trainer_success() {
        Trainer trainer = Trainer.builder()
                .name("zhangsan")
                .grouped(false)
                .build();
        TrainerEntity trainerEntity = Convert.toTrainerEntity(trainer);
        when(trainerRepository.save(trainerEntity)).thenReturn(trainerEntity);

        TrainerResponse newUser = trainerService.createTrainer(trainer);
        Assertions.assertThat(trainerEntity.getName()).isEqualTo(newUser.getName());
    }

}
