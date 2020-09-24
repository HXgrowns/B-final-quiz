package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
