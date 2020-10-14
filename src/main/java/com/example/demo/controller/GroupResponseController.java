package com.example.demo.controller;

import com.example.demo.entity.GroupEntity;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groups")
@Validated
public class GroupResponseController {
    private final GroupService GROUPSERVICE;

    public GroupResponseController(GroupService groupService) {
        this.GROUPSERVICE = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupEntity>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body( GROUPSERVICE.findAll());
    }

    @PostMapping("/auto-grouping")
    public ResponseEntity<List<GroupEntity>> autoGroup() {
        return ResponseEntity.ok().body(GROUPSERVICE.autoGroup());
    }

    @PatchMapping("/{id}")
    // GTB: - 违反Restful, Patch接口要修改新字段应该通过RequestBody传递，并用对象来接收
    public ResponseEntity<GroupEntity> updateNameById(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok().body(GROUPSERVICE.updateNameById(id, name));
    }
}
