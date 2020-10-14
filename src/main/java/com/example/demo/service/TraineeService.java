package com.example.demo.service;

import com.example.demo.dto.Trainee;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.response.TraineeResponse;
import com.example.demo.utils.Convert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeService {
    private final TraineeRepository TRAINEEREPOSITORY;

    public TraineeService(TraineeRepository TRAINEEREPOSITORY) {
        this.TRAINEEREPOSITORY = TRAINEEREPOSITORY;
    }

    public List<TraineeResponse> findNotGroup(Boolean grouped) {
        // GTB: - 下面的逻辑存在重复代码，可以进一步简化
        List<TraineeEntity> traineeEntities;
        if (grouped == null) {
            traineeEntities = TRAINEEREPOSITORY.findAll();
        } else if (grouped) {
            traineeEntities = TRAINEEREPOSITORY.findByGroupNotNull();
        } else {
            traineeEntities = TRAINEEREPOSITORY.findByGroupNull();
        }
        return traineeEntities
                .stream()
                .map(Convert::toTraineeResponse)
                .collect(Collectors.toList());

    }

    public TraineeResponse createTrainee(Trainee trainee) {
        TraineeEntity traineeEntity = TRAINEEREPOSITORY.save(Convert.toTraineeEntity(trainee));
        return Convert.toTraineeResponse(traineeEntity);
    }

    public void delete(Long id) {
        TraineeEntity traineeEntity = TRAINEEREPOSITORY.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.TRAINEE_NOT_FOUND));
        TRAINEEREPOSITORY.delete(traineeEntity);
    }
}
