package com.example.demo.controller;

import com.example.demo.dto.Trainee;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.response.TraineeResponse;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainees")
@Validated
public class TraineeController {
    private TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<List<TraineeEntity>> findNotGroup(@RequestParam(required = false) Boolean grouped) {
        return ResponseEntity.status(HttpStatus.OK).body( traineeService.findNotGroup(grouped));
    }

    @PostMapping
    public ResponseEntity<TraineeResponse> createTrainee(@RequestBody @Valid Trainee trainee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traineeService.createTrainee(trainee));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotNull Long id) {
        traineeService.delete(id);
    }
}
