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
    private TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<TrainerResponse> findNotGroup(Boolean grouped) {
        if(grouped == null) {
            return trainerRepository.findAll()
                    .stream()
                    .map(Convert::toTrainerResponse)
                    .collect(Collectors.toList());
        } else if (grouped) {
            return trainerRepository.findByGroupNotNull()
                    .stream()
                    .map(Convert::toTrainerResponse)
                    .collect(Collectors.toList());
        }

        return trainerRepository.findByGroupNull()
                .stream()
                .map(Convert::toTrainerResponse)
                .collect(Collectors.toList());
    }

    public TrainerResponse createTrainer(Trainer trainer) {
        TrainerEntity trainerEntity = trainerRepository.save(Convert.toTrainerEntity(trainer));
        return Convert.toTrainerResponse(trainerEntity);
    }

    public void delete(Long id) {
        TrainerEntity trainerEntity = trainerRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.TRAINEE_NOT_FOUND));
        trainerRepository.delete(trainerEntity);
    }
}
