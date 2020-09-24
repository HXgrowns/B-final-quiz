package com.example.demo.controller;

import com.example.demo.dto.Trainee;
import com.example.demo.response.TraineeResponse;
import com.example.demo.service.TraineeService;
import com.example.demo.utils.Convert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TraineeService traineeService;
    @Autowired
    private JacksonTester<Trainee> traineeJacksonTester;

    @Nested
    class GetTraineeListByGrouped {
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

    @Nested
    class CreateTrainee {
        private Trainee newTrainee;
        private TraineeResponse newTraineeResponse;

        @BeforeEach
        void setUp() {
            newTrainee = Trainee.builder()
                    .name("lisi")
                    .office("武汉")
                    .zoomId("1234")
                    .github("12345")
                    .email("hu@thoughtworks.com")
                    .build();
            newTraineeResponse = Convert.toTraineeResponse(Convert.toTraineeEntity(newTrainee));
        }

        @Nested
        class WhenTraineeIsValid {
            @Test
            public void should_add_trainee_with_jsonPath() throws Exception {
                when(traineeService.createTrainee(newTrainee)).thenReturn(newTraineeResponse);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/v1/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJacksonTester.write(newTrainee).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", is("lisi")))
                        .andExpect(jsonPath("$.office", is("武汉")));

                verify(traineeService).createTrainee(newTrainee);
            }
        }
        @Nested
        class WhenTraineeIsInValid {

            Trainee inValidTrainee = Trainee.builder()
                    .name(null)
                    .office("武汉")
                    .zoomId("1234")
                    .github("12345")
                    .email("hu@thoughtworks.com")
                    .build();

            @Test
            public void should_throw_exception_with_jsonPath() throws Exception {
                when(traineeService.createTrainee(inValidTrainee)).thenThrow(new RuntimeException("name is null"));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/v1/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJacksonTester.write(inValidTrainee).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", is("name is null")));
            }
        }
    }

}