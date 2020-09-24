package com.example.demo.controller;

import com.example.demo.entity.GroupEntity;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/groups")
@Validated
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupEntity>> findTraineeNotGroup() {
        return ResponseEntity.status(HttpStatus.OK).body( groupService.findAll());
    }

    @PostMapping("/auto-grouping")
    public ResponseEntity<List<GroupEntity>> createGroup() {
        return ResponseEntity.ok().body(groupService.createGroup());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupEntity> updateByTeamName(@PathVariable @NotBlank Long id, @RequestParam String teamName) {
        return ResponseEntity.ok().body(groupService.updateByTeamName(id, teamName));
    }

}
