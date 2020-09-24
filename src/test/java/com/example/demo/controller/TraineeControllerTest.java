package com.example.demo.controller;

import com.example.demo.dto.Trainee;
import com.example.demo.entity.TraineeEntity;
import com.example.demo.response.TraineeResponse;
import com.example.demo.service.TraineeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureJsonTesters
class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TraineeService traineeService;
    @Autowired
    private JacksonTester<Trainee> traineeJacksonTester;

    @Nested
    class GetTraineeListByGrouped{
        @AfterEach
        void tearDown() throws UnsupportedEncodingException {
            Mockito.reset(traineeService);
        }

            @Test
            public void should_return_trainees_by_grouped_with_jsonPath() throws Exception {
                when(traineeService.findTrainee(false))
                        .thenReturn(Collections.singletonList(TraineeResponse.builder()
                                .id(1L)
                                .name("张三").build()));

                mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/v1/trainees?grouped=false"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id", is(1)))
                        .andExpect(jsonPath("$[0].name", is("张三")));
                verify(traineeService, times(1)).findTrainee(false);
            }


    }

}