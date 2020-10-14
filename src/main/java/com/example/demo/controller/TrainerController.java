package com.example.demo.controller;

import com.example.demo.dto.Trainer;
import com.example.demo.response.TrainerResponse;
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
public class TrainerController {
    private final TrainerService TRAINERSERVICE;

    public TrainerController(TrainerService TRAINERSERVICE) {
        this.TRAINERSERVICE = TRAINERSERVICE;
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponse>> findNotGroup(@RequestParam(required = false,value = "grouped") Boolean grouped) {
        return ResponseEntity.status(HttpStatus.OK).body( TRAINERSERVICE.findNotGroup(grouped));
    }

    @PostMapping
    public ResponseEntity<TrainerResponse> createTrainer(@RequestBody @Valid Trainer trainer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TRAINERSERVICE.createTrainer(trainer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(value = "id") @NotNull Long id) {
        TRAINERSERVICE.delete(id);
    }
}
