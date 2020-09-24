package com.example.demo.service;

import com.example.demo.dto.Trainee;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.response.TraineeResponse;
import com.example.demo.utils.Convert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        traineeService = new TraineeService(traineeRepository);
    }
    @Test
    void should_return_trainees_by_grouped() {
        //List<TraineeEntity> traineeEntities = Arrays.asList(TraineeEntity.builder().id(1L).name("张三").build(), TraineeEntity.builder().id(2L).name("李四").build());
        //when(traineeRepository.findByGroupNotNull()).thenReturn(null);
        //when(traineeRepository.findByGroupNull()).thenReturn(traineeEntities);
        //
        //List<TraineeResponse> result = traineeService.findNotGroup(false);
        //Assertions.assertThat(traineeEntities.get(0).getName()).isEqualTo(result.get(0).getName());
        //Assertions.assertThat(traineeEntities.get(1).getName()).isEqualTo(result.get(1).getName());
    }

    @Test
    void should_create_trainee_success() {
        Trainee trainee = Trainee.builder()
                .name("zhangsan")
                .email("hu@Thoughtworks.com")
                .github("1234")
                .zoomId("1234")
                .office("武汉")
                .grouped(false)
                .build();
        TraineeEntity traineeEntity = Convert.toTraineeEntity(trainee);
        when(traineeRepository.save(traineeEntity)).thenReturn(traineeEntity);

        TraineeResponse newUser = traineeService.createTrainee(trainee);
        Assertions.assertThat(traineeEntity.getName()).isEqualTo(newUser.getName());
    }

}
