package com.example.demo.controller;

import com.example.demo.dto.Trainer;
import com.example.demo.dto.Trainer;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.response.TrainerResponse;
import com.example.demo.response.TrainerResponse;
import com.example.demo.service.TrainerService;
import com.example.demo.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainers")
@Validated
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponse>> findNotGroup(@RequestParam(required = false) Boolean grouped) {
        return ResponseEntity.status(HttpStatus.OK).body( trainerService.findNotGroup(grouped));
    }

    @PostMapping
    public ResponseEntity<TrainerResponse> createTrainer(@RequestBody @Valid Trainer trainer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainerService.createTrainer(trainer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotNull Long id) {
        trainerService.delete(id);
    }
}
