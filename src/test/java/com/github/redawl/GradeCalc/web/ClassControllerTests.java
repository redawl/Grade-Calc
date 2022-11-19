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
public class ClassControllerTests {
    private final MockMvc mockMvc;

    private final String endpoint = "/api/class";

    private final String testAssignmentName = "Testing";
    private final String testClassName = "Testing Class";

    @Autowired
    public ClassControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        // Add assignment
        Assignment assignment = new Assignment();
        assignment.setAssignmentName(testAssignmentName);
        assignment.setClassName(testClassName);
        assignment.setAssignmentWeight(.5);
        assignment.setAssignmentScore(100);

        try{
            mockMvc.perform(post("/api/assignment")
                            .contentType(MediaType.APPLICATION_JSON)
                    .content(assignment.toJSON())).andExpect(status().isCreated());
        } catch (Exception ex){
            // Do nothing
        }
    }

    @Test
    void getClassShouldReturnExpectedClass(){
        try{
            mockMvc.perform(get(endpoint))
                    .andExpect(status().isOk())
                    .andExpect(content().json(String.format("[{\"className\": \"%s\"}]", testClassName)));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

}
