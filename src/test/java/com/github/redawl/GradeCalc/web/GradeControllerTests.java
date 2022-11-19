package com.github.redawl.GradeCalc.web;

import com.github.redawl.GradeCalc.Assignment.Assignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class GradeControllerTests {
    private final MockMvc mockMvc;

    private final String endpoint = "/api/grade";

    private final String testAssignmentName = "Testing";
    private final String testClassName = "Testing Class";
    private final double testAssignmentWeight = 0.5;
    private final double testAssignmentScore = 50;
    @Autowired
    public GradeControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        // Add assignment to db
        Assignment assignment = new Assignment();
        assignment.setAssignmentName(testAssignmentName);
        assignment.setClassName(testClassName);
        assignment.setAssignmentWeight(testAssignmentWeight);
        assignment.setAssignmentScore(testAssignmentScore);
        try {
            mockMvc.perform(post("/api/assignment").content(assignment.toJSON())
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception ex){
            // do nothing
        }
    }

    @Test
    void computeMaxGradeShouldReturnCorrectValue(){
        try{
            mockMvc.perform(get(endpoint + "/max").queryParam("className", testClassName))
                    .andExpect(status().isOk()).andExpect(content().json("{ \"grade\": 75}"));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void computeCurrentGradeShouldReturnCorrectValue(){
        try {
            mockMvc.perform(get(endpoint).queryParam("className", testClassName))
                    .andExpect(status().isOk())
                    .andExpect(content().json(String.format("{\"grade\":  %f}",testAssignmentScore * testAssignmentWeight)));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void computeRequiredGradeShouldReturnCorrectValue(){
        try {
            mockMvc.perform(get(endpoint + "/required").queryParam("className", testClassName)
                            .queryParam("targetGrade", "70"))
                    .andExpect(status().isOk()).andExpect(content().json("{\"grade\": 90}"));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
