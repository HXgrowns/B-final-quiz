package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainee {
    private Long id;
    private String name;
    private String office;
    private String email;
    private String github;
    private String zoomId;
    private Boolean grouped;
}
