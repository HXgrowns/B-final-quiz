package com.example.demo.service;

import com.example.demo.dto.Trainer;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.response.TrainerResponse;
import com.example.demo.utils.Convert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerService {
    private final TrainerRepository TRAINERREPOSITORY;

    public TrainerService(TrainerRepository TRAINERREPOSITORY) {
        this.TRAINERREPOSITORY = TRAINERREPOSITORY;
    }

    public List<TrainerResponse> findNotGroup(Boolean grouped) {
        List<TrainerEntity> trainerEntities;
        if (grouped == null) {
            trainerEntities = TRAINERREPOSITORY.findAll();
        } else if (grouped) {
            trainerEntities = TRAINERREPOSITORY.findByGroupNotNull();
        } else {
            trainerEntities = TRAINERREPOSITORY.findByGroupNull();
        }

        return trainerEntities
                .stream()
                .map(Convert::toTrainerResponse)
                .collect(Collectors.toList());
    }

    public TrainerResponse createTrainer(Trainer trainer) {
        TrainerEntity trainerEntity = TRAINERREPOSITORY.save(Convert.toTrainerEntity(trainer));
        return Convert.toTrainerResponse(trainerEntity);
    }

    public void delete(Long id) {
        TrainerEntity trainerEntity = TRAINERREPOSITORY.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.TRAINEE_NOT_FOUND));
        TRAINERREPOSITORY.delete(trainerEntity);
    }
}
