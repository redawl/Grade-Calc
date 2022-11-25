package com.github.redawl.GradeCalc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.redawl.GradeCalc.Assignment.AssignmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AssignmentControllerTests {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String testingUser;
    private static final String endpoint = "/api/assignment";
    private final MockMvc mockMvc;
    @Autowired
    public AssignmentControllerTests(MockMvc mockMvc, @Value("${testingUser}") String testingUser) {
        this.mockMvc = mockMvc;
        this.testingUser = testingUser;
    }

    /**
     * Null or empty tests
     */
    @ParameterizedTest
    @NullAndEmptySource
    void getShouldReturnBadRequestForInvalidClassNames(String className){
        try {
            mockMvc.perform(get(endpoint)
                            .with(user(testingUser))
                            .queryParam("className", className))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deleteShouldReturnBadRequestForInvalidClassNames(String source){
        try {
            mockMvc.perform(delete(endpoint)
                            .with(user(testingUser))
                            .queryParam("assignmentName", source)
                            .queryParam("className", source))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void deleteShouldReturnNotFoundForNonExistentAssignment(){
        try {
            mockMvc.perform(delete(endpoint)
                            .with(user(testingUser))
                            .queryParam("assignmentName", "***************")
                            .queryParam("className", "**************"))
                    .andExpect(status().isNotFound());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"Invalid json", "{}", "[]", "{}", "{\"incorrect\":\"params\"}"})
    void postShouldReturnBadRequestForInvalidClassNames(String body){
        try {
            mockMvc.perform(post(endpoint)
                            .with(user(testingUser))
                            .content(body)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void postShouldReturnOkOnValidClassName(){
        String className = "Test Class";
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentWeight(0.4);
        assignmentDto.setAssignmentScore(100.0);
        assignmentDto.setClassName(className);
        assignmentDto.setAssignmentName("Midterm");

        try {
            mockMvc.perform(post("/api/assignment")
                            .with(user("admin"))
                            .content(objectMapper.writeValueAsString(assignmentDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
