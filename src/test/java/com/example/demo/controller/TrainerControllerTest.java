package com.example.demo.controller;

import com.example.demo.dto.Trainer;
import com.example.demo.response.TrainerResponse;
import com.example.demo.service.TrainerService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
public class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrainerService trainerService;
    @Autowired
    private JacksonTester<Trainer> trainerJacksonTester;

    @Nested
    class GetTrainerListByGrouped {
        @AfterEach
        void tearDown() throws UnsupportedEncodingException {
            Mockito.reset(trainerService);
        }

        @Test
        public void should_return_trainers_by_grouped_with_jsonPath() throws Exception {
            when(trainerService.findNotGroup(false))
                    .thenReturn(Collections.singletonList(TrainerResponse.builder()
                            .id(1L)
                            .name("张三").build()));

            mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/trainers?grouped=false"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("张三")));
            verify(trainerService, times(1)).findNotGroup(false);
        }

    }

    @Nested
    class CreateTrainer {
        private Trainer newTrainer;
        private TrainerResponse newTrainerResponse;

        @BeforeEach
        void setUp() {
            newTrainer = Trainer.builder()
                    .name("lisi")
                    .build();
            newTrainerResponse = Convert.toTrainerResponse(Convert.toTrainerEntity(newTrainer));
        }

        @Nested
        class WhenTrainerIsValid {
            @Test
            public void should_add_trainer_with_jsonPath() throws Exception {
                when(trainerService.createTrainer(newTrainer)).thenReturn(newTrainerResponse);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainerJacksonTester.write(newTrainer).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name", is("lisi")));

                verify(trainerService).createTrainer(newTrainer);
            }
        }

        @Nested
        class WhenTrainerIsInValid {

            Trainer inValidTrainer = Trainer.builder()
                    .name(null)
                    .build();

            @Test
            public void should_throw_exception_with_jsonPath() throws Exception {
                when(trainerService.createTrainer(inValidTrainer)).thenThrow(new RuntimeException("name is null"));
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainerJacksonTester.write(inValidTrainer).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", is("name is null")));
            }
        }
    }

}
