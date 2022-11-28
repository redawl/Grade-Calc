package com.github.redawl.gradecalc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.redawl.gradecalc.assignment.AssignmentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class GradeResponseControllerTests {

    private final ObjectMapper objectMapper =  new ObjectMapper();

    private final String testingUser;

    private final MockMvc mockMvc;

    private final String endpoint = "/api/grade";

    private final String testAssignmentName = "Testing";
    private final String testClassName = "Testing Class";
    private final double testAssignmentWeight = 0.5;
    private final double testAssignmentScore = 50;
    @Autowired
    public GradeResponseControllerTests(MockMvc mockMvc, @Value("${testingUser}") String testingUser) {
        this.mockMvc = mockMvc;
        this.testingUser = testingUser;

        // Add assignment to db
        AssignmentDTO assignmentDto = AssignmentDTO.builder()
                .assignmentName(testAssignmentName)
                .className(testClassName)
                .assignmentWeight(testAssignmentWeight)
                .assignmentScore(testAssignmentScore)
                .build();
        try {
            mockMvc.perform(post("/api/assignment")
                    .with(user(testingUser))
                    .content(objectMapper.writeValueAsString(assignmentDto))
                    .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception ex){
            // do nothing
        }
    }

    @Test
    void computeMaxGradeShouldReturnCorrectValue(){
        try{
            mockMvc.perform(get(endpoint + "/max")
                            .with(user(testingUser))
                            .queryParam("className", testClassName))
                    .andExpect(status().isOk()).andExpect(content().json("{ \"grade\": 75}"));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void computeCurrentGradeShouldReturnCorrectValue(){
        try {
            mockMvc.perform(get(endpoint)
                            .with(user(testingUser))
                            .queryParam("className", testClassName))
                    .andExpect(status().isOk())
                    .andExpect(content().json(String.format("{\"grade\":  %f}",testAssignmentScore * testAssignmentWeight)));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @Test
    void computeRequiredGradeShouldReturnCorrectValue(){
        try {
            mockMvc.perform(get(endpoint + "/required")
                            .with(user(testingUser))
                            .queryParam("className", testClassName)
                            .queryParam("targetGrade", "70"))
                    .andExpect(status().isOk()).andExpect(content().json("{\"grade\": 90}"));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
