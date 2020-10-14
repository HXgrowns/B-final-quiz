package com.example.demo.controller;

import com.example.demo.dto.Trainee;
import com.example.demo.response.TraineeResponse;
import com.example.demo.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainees")
// GTB：- 没有请求参数校验，不用加@Validated
public class TraineeController {
    // GET: - 构造器注入的属性推荐使用final
    private final TraineeService TRAINEESERVICE;

    public TraineeController(TraineeService TRAINEESERVICE) {
        this.TRAINEESERVICE = TRAINEESERVICE;
    }

    @GetMapping
    // GTB: - @RequestParam应该显式写明value属性
    public ResponseEntity<List<TraineeResponse>> findNotGroup(@RequestParam(required = false, value = "grouped") Boolean grouped) {
        return ResponseEntity.status(HttpStatus.OK).body( TRAINEESERVICE.findNotGroup(grouped));
    }

    @PostMapping
    public ResponseEntity<TraineeResponse> createTrainee(@RequestBody @Valid Trainee trainee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TRAINEESERVICE.createTrainee(trainee));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // GTB: - @PathVariable应该显式写明value属性
    public void deleteById(@PathVariable(value = "id") @NotNull Long id) {
        TRAINEESERVICE.delete(id);
    }
}
