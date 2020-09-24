package com.example.demo.service;

import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import com.example.demo.response.GroupResponse;
import com.example.demo.response.TraineeResponse;
import com.example.demo.response.TrainerResponse;
import com.example.demo.utils.Convert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Transactional
    public List<GroupResponse> autoGroupting() {
        trainerRepository.deleteGroup(null);
        traineeRepository.deleteGroup(null);
        groupRepository.deleteAll();
        trainerRepository.flush();
        traineeRepository.flush();
        groupRepository.flush();

        List<TraineeEntity> traineeEntities = traineeRepository.findAll();
        List<TrainerEntity> trainerEntities = trainerRepository.findAll();
        if (trainerEntities.size() < 2) {
            throw new BusinessException(ExceptionEnum.GROUP_NOT_ENOUGT);
        }

        List<GroupEntity> groupEntities = getGroups(trainerEntities.size());
        List<GroupResponse> groups = groupEntities.stream().map(Convert::toGroupResponse).collect(Collectors.toList());
        groupRepository.saveAll(groupEntities);
        groupRepository.flush();

        Map<Long, List<TrainerResponse>> groudIdToTrainers = groupForTrainer(trainerEntities, groups.size());
        Map<Long, List<TraineeResponse>> groudIdToTrainees = groupForTrainee(traineeEntities, groups.size());
        for (GroupResponse group : groups) {
            group.setTrainers(groudIdToTrainers.get(group.getId()));
            group.setTrainees(groudIdToTrainees.get(group.getId()));
        }
        return groups;
    }

    private List<GroupEntity> getGroups(long trainerCount) {
        List<GroupEntity> groups = new ArrayList<>();

        long teamCount = trainerCount / 2;
        for (long i = 0; i < teamCount; i++) {
            long id = i + 1;
            groups.add(GroupEntity.builder().id(id).name(id + "组").build());
        }
        return groups;
    }

    private Map<Long, List<TrainerResponse>> groupForTrainer(List<TrainerEntity> trainerEntities, long teamCount) {
        Map<Long, List<TrainerResponse>> groudIdToTrainers = new HashMap<>();

        Collections.shuffle(trainerEntities);
        for (int i = 0; i < trainerEntities.size() && i < teamCount * 2; i++) {
            long groudId = i % teamCount + 1;
            TrainerEntity trainerEntity = trainerEntities.get(i);
            trainerRepository.updateGroupId(groudId, trainerEntity.getId());

            List<TrainerResponse> trainers = groudIdToTrainers.getOrDefault(groudId, new ArrayList<>());
            trainers.add(Convert.toTrainerResponse(trainerEntity));
            groudIdToTrainers.put(groudId, trainers);
        }

        return groudIdToTrainers;
    }

    private Map<Long, List<TraineeResponse>> groupForTrainee(List<TraineeEntity> traineeEntities, long teamCount) {
        Map<Long, List<TraineeResponse>> groudIdToTrainees = new HashMap<>();

        Collections.shuffle(traineeEntities);
        for (int i = 0; i < traineeEntities.size(); i++) {
            long groudId = i % teamCount + 1;
            TraineeEntity traineeEntity = traineeEntities.get(i);
            traineeRepository.updateGroupId(i % teamCount + 1, traineeEntity.getId());

            List<TraineeResponse> trainees = groudIdToTrainees.getOrDefault(groudId, new ArrayList<>());
            trainees.add(Convert.toTraineeResponse(traineeEntity));
            groudIdToTrainees.put(groudId, trainees);
        }

        return groudIdToTrainees;
    }

    public GroupEntity updateNameById(Long id, String name) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.GROUP_NOT_FOUND));
        if (groupRepository.findByIdNotAndName(id, name) != null) {
            throw new BusinessException(ExceptionEnum.GROUP_NAME_EXIST);
        }
        groupEntity.setName(name);
        return groupRepository.save(groupEntity);
    }
}
