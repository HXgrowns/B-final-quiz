package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraineeResponse {
    private Long id;

    private String name;
    private String office;
    private String email;
    private String github;
    private String zoomId;
}
