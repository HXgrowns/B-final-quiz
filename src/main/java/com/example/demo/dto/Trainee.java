package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainee {
    private Long id;
    @NotBlank(message = "name is null")
    private String name;
    @NotBlank(message = "office is null")
    private String office;
    @NotBlank(message = "email is null")
    @Email(message = "email is invalid")
    private String email;
    @NotBlank(message = "github is null")
    private String github;
    @NotBlank(message = "zoomId is null")
    private String zoomId;
    private Boolean grouped;
    private Long groupId;
}
