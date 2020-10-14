package com.example.demo.service;

import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.entity.TrainerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.ExceptionEnum;
import com.example.demo.exception.GroupException;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.TraineeRepository;
import com.example.demo.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private static final Integer MIN_GROUP = 2;
    private static final String GROUP_UNIT = " 组";

    public GroupService(GroupRepository groupRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    public List<GroupEntity> findAll() {
        // GTB: - 没有使用@OneToMany注解处理一对多关系
        return groupRepository.findAll();
    }

    @Transactional
    // GTB: - 拼写错误
    public List<GroupEntity> autoGroup() {
        init();
        List<TrainerEntity> allTrainer = trainerRepository.findAll();
        int trainerNumber = allTrainer.size();
        if (trainerNumber < MIN_GROUP) {
            throw new GroupException(ExceptionEnum.GROUP_NOT_ENOUGT);
        }
        List<TraineeEntity> allTrainee = traineeRepository.findAll();
        Collections.shuffle(allTrainee);
        Collections.shuffle(allTrainer);
        int traineeNumber = allTrainee.size();
        int groupNumber = trainerNumber / MIN_GROUP;
        int overflowTrainee = traineeNumber % groupNumber;

        int previousTraineeNumber = 0;
        int previousTrainerNumber = 0;

        for (int groupId = 1; groupId <= groupNumber; groupId++) {
            GroupEntity group = GroupEntity.builder().id((long)groupId).name(groupId + GROUP_UNIT).build();
            int groupTraineeNumber = traineeNumber / groupNumber;
            if (overflowTrainee > 0) {
                groupTraineeNumber += 1;
                overflowTrainee -= 1;
            }
            List<TraineeEntity> trainees = allTrainee.stream()
                    .skip(previousTraineeNumber)
                    .limit(groupTraineeNumber)
                    .peek(t -> t.setGroup(group)).collect(Collectors.toList());
            List<TrainerEntity> trainers = allTrainer.stream()
                    .skip(previousTrainerNumber)
                    .limit(MIN_GROUP)
                    .peek(t -> t.setGroup(group)).collect(Collectors.toList());
            group.setTraineeEntities(trainees);
            group.setTrainerEntities(trainers);

            groupRepository.save(group);
            previousTraineeNumber += groupTraineeNumber;
            previousTrainerNumber += MIN_GROUP;
        }
        return groupRepository.findAll();
    }

    private void init() {
        trainerRepository.deleteGroup(null);
        traineeRepository.deleteGroup(null);
        groupRepository.deleteAll();
        trainerRepository.flush();
        traineeRepository.flush();
        groupRepository.flush();
    }

    public GroupEntity updateNameById(Long id, String name) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new BusinessException(ExceptionEnum.GROUP_NOT_FOUND));
        // GTB - BUG: 修改小组名与原小组名相同时会报错
        if (groupRepository.findByIdNotAndName(id, name) != null) {
            throw new BusinessException(ExceptionEnum.GROUP_NAME_EXIST);
        }
        groupEntity.setName(name);
        return groupRepository.save(groupEntity);
    }
}
