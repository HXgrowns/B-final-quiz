package com.example.demo.utils;

import com.example.demo.dto.Trainee;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.response.TraineeResponse;

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
                .grouped(trainee.getGrouped())
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
}
