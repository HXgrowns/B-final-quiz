package com.example.demo.entity;

import lombok.*;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TraineeEntity> traineeEntities;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TrainerEntity> trainerEntities;
}
