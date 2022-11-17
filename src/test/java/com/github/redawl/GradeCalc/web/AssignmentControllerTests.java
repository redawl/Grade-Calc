package com.github.redawl.GradeCalc.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.redawl.GradeCalc.Assignment.Assignment;
import org.hibernate.annotations.Parameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    void shouldReturnBadRequestForInvalidClassNames(String className){
//        try {
//            mockMvc.perform(get("/api/grade").queryParam("className", className))
//                    .andExpect(status().isBadRequest());
//        } catch (Exception ex){
//            Assertions.fail("Test should not throw exception", ex);
//        }
//    }
//
//    @Test
//    void shouldReturnOkOnValidClassName(){
//        String className = "Test Class";
//        Assignment assignment = new Assignment();
//        assignment.setAssignmentWeight(0.4);
//        assignment.setAssignmentValue(100.0);
//        assignment.setClassName(className);
//        assignment.setAssignmentName("Midterm");
//
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String body = null;
//        try {
//            body = ow.writeValueAsString(assignment);
//        } catch (JsonProcessingException ex){
//            Assertions.fail("Test should not throw exception", ex);
//        }
//
//        try {
//            mockMvc.perform(post("/api/assignment").content(body)).andExpect(status().isCreated());
//        } catch (Exception ex){
//            Assertions.fail("Test should not throw exception", ex);
//        }
//    }
}
