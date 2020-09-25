package com.example.demo.controller;

import com.example.demo.entity.GroupEntity;
import com.example.demo.response.GroupResponse;
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
@RequestMapping("/groups")
@Validated
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body( groupService.findAll());
    }

    @PostMapping("/auto-grouping")
    public ResponseEntity<List<GroupResponse>> autoGroupting() {
        return ResponseEntity.ok().body(groupService.autoGroupting());
    }

    @PatchMapping("/{id}")
    // GTB: - 违反Restful, Patch接口要修改新字段应该通过RequestBody传递，并用对象来接收
    public ResponseEntity<GroupEntity> updateNameById(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok().body(groupService.updateNameById(id, name));
    }
}
