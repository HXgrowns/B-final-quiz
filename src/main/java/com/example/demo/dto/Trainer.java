package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {
    private Long id;
    @NotBlank(message = "name is null")
    private String name;
    private Boolean grouped;
    private Long groupId;
}
