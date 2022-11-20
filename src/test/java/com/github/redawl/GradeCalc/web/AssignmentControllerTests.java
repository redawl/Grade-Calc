package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Assignment.Assignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AssignmentControllerTests {

    private static final String endpoint = "/api/assignment";
    private final MockMvc mockMvc;


    public AssignmentControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Null or empty tests
     */
    @ParameterizedTest
    @NullAndEmptySource
    void getShouldReturnBadRequestForInvalidClassNames(String className){
        try {
            mockMvc.perform(get(endpoint).queryParam("className", className))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deleteShouldReturnBadRequestForInvalidClassNames(String source){
        try {
            mockMvc.perform(delete(endpoint).queryParam("assignmentName", source)
                            .queryParam("className", source))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void deleteShouldReturnNotFoundForNonExistentAssignment(){
        try {
            mockMvc.perform(delete(endpoint).queryParam("assignmentName", "***************")
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
            mockMvc.perform(post(endpoint).content(body).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void postShouldReturnOkOnValidClassName(){
        String className = "Test Class";
        Assignment assignment = new Assignment();
        assignment.setAssignmentWeight(0.4);
        assignment.setAssignmentScore(100.0);
        assignment.setClassName(className);
        assignment.setAssignmentName("Midterm");

        try {
            mockMvc.perform(post("/api/assignment")
                            .content(assignment.toJSON())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
