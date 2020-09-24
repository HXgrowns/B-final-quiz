package com.example.demo.service;

import com.example.demo.dto.Trainee;
import com.example.demo.dto.Trainer;
import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;

    public GroupService(GroupRepository groupRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    public List<GroupEntity> createGroup() {
        List<TrainerEntity> trainerEntities = trainerRepository.findAll();
        if (trainerEntities.size() < 2) {
            throw new BusinessException(ExceptionEnum.GROUP_NOT_ENOUGT);
        }
        Collections.shuffle(trainerEntities);
        List<GroupEntity> groupEntities = groupForTrainer(trainerEntities);
        List<TraineeEntity> traineeEntities = traineeRepository.findAll();
        Collections.shuffle(traineeEntities);
        groupForTrainee(traineeEntities, groupEntities.size());
        return null;
    }

    private List<GroupEntity> groupForTrainer(List<TrainerEntity> trainerEntities) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        Collections.shuffle(trainerEntities);
        int teamCount = trainerEntities.size() / 2;
        int start = 0;
        for (int i = 0; i < teamCount; i++) {
            List<TrainerEntity> subTrainerEntity = trainerEntities.subList(start,start + 2);
            groupEntities.get(i).setName(i + "组");
            groupEntities.get(i).setTrainers(subTrainerEntity);
            groupRepository.save(groupEntities.get(i));
            start += 2;
        }
        return groupEntities;
    }

    private void groupForTrainee(List<TraineeEntity> traineeEntities, int teamCount) {
        List<GroupEntity> groupEntities = new ArrayList<>();
        Collections.shuffle(traineeEntities);
        int teamSize = traineeEntities.size() / teamCount;
        int traineeRestCount = traineeEntities.size() % teamSize;
        int start = 0;
        for (int i = 0; i < teamCount; i++) {
            List<TraineeEntity> subTraineeEntities = traineeEntities.subList(start, start + teamSize);
            groupEntities.get(i).setName(i + "组");
            groupEntities.get(i).setTrainees(subTraineeEntities);
            groupRepository.save(groupEntities.get(i));
            start += teamSize;
        }

        for (int i = 0; i < traineeRestCount; i++) {
            List<TraineeEntity> teamTraineeEntity = groupEntities.get(i).getTrainees();
            List<TraineeEntity> resStudent = traineeEntities.subList(start, start + 1);
            List<TraineeEntity> newTrainees = new ArrayList<>();
            newTrainees.addAll(teamTraineeEntity);
            newTrainees.addAll(resStudent);
            groupEntities.get(i).setTrainees(newTrainees);
            groupRepository.save(groupEntities.get(i));
            start += 1;
        }

    }

    public GroupEntity updateByTeamName(Long id, String teamName) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.GROUP_NOT_FOUND));
        List<GroupEntity> groupEntities = groupRepository.findAll();
        Optional<GroupEntity> first = groupEntities.stream().filter(group -> teamName.equals(group.getName())).findFirst();
        if (first.isPresent()) {
            throw new BusinessException(ExceptionEnum.GROUP_NAME_EXIST);
        }
        groupEntity.setName(teamName);
        return groupEntity;
    }
}
