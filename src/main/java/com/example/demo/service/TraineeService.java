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
    private TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public List<TraineeResponse> findNotGroup(Boolean grouped) {
        if(grouped == null) {
            return traineeRepository.findAll()
                    .stream()
                    .map(Convert::toTraineeResponse)
                    .collect(Collectors.toList());
        } else if (grouped) {
            return traineeRepository.findByGroupNotNull()
                    .stream()
                    .map(Convert::toTraineeResponse)
                    .collect(Collectors.toList());
        }
        return traineeRepository.findByGroupNull()
                .stream()
                .map(Convert::toTraineeResponse)
                .collect(Collectors.toList());

    }

    public TraineeResponse createTrainee(Trainee trainee) {
        TraineeEntity traineeEntity = traineeRepository.save(Convert.toTraineeEntity(trainee));
        return Convert.toTraineeResponse(traineeEntity);
    }

    public void delete(Long id) {
        TraineeEntity traineeEntity = traineeRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.TRAINEE_NOT_FOUND));
        traineeRepository.delete(traineeEntity);
    }
}
