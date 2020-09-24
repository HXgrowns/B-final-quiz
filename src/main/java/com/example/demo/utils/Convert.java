package com.example.demo.utils;

import com.example.demo.controller.GroupController;
import com.example.demo.dto.Trainee;
import com.example.demo.dto.Trainer;
import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.response.GroupResponse;
import com.example.demo.response.TraineeResponse;
import com.example.demo.response.TrainerResponse;

import java.util.ArrayList;

public class Convert {
    public static TraineeEntity toTraineeEntity(Trainee trainee) {
        if(trainee == null) {
            return null;
        }
        return TraineeEntity.builder()
                .id(trainee.getId())
                .name(trainee.getName())
                .email(trainee.getEmail())
                .github(trainee.getGithub())
                .office(trainee.getOffice())
                .zoomId(trainee.getZoomId())
                .build();
    }
    public static TraineeResponse toTraineeResponse(TraineeEntity traineeEntity) {
        if (traineeEntity == null) {
            return null;
        }
        return TraineeResponse.builder()
                .id(traineeEntity.getId())
                .name(traineeEntity.getName())
                .email(traineeEntity.getEmail())
                .github(traineeEntity.getGithub())
                .office(traineeEntity.getOffice())
                .zoomId(traineeEntity.getZoomId())
                .build();
    }

    public static TrainerEntity toTrainerEntity(Trainer trainer) {
        if (trainer == null) {
            return null;
        }
        return TrainerEntity.builder()
                .id(trainer.getId())
                .name(trainer.getName())
                .build();
    }

    public static TrainerResponse toTrainerResponse(TrainerEntity trainerEntity) {
        if (trainerEntity == null) {
            return null;
        }
        return TrainerResponse.builder()
                .id(trainerEntity.getId())
                .name(trainerEntity.getName())
                .build();
    }

    public static GroupResponse toGroupResponse(GroupEntity groupEntity) {
        return new GroupResponse(groupEntity.getId(), groupEntity.getName(), new ArrayList<>(), new ArrayList<>());
    }
}
