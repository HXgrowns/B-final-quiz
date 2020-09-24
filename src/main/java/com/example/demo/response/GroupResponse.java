package com.example.demo.response;

import com.example.demo.dto.Trainee;
import com.example.demo.dto.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResponse {
    private Long id;
    private String name;
    private List<TrainerResponse> trainers;
    private List<TraineeResponse> trainees;
}
