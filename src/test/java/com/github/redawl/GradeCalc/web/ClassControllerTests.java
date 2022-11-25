package com.github.redawl.GradeCalc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.redawl.GradeCalc.Assignment.AssignmentDto;
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
public class ClassControllerTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String testingUser;
    private final MockMvc mockMvc;

    private final String endpoint = "/api/class";

    private final String testAssignmentName = "Testing";
    private final String testClassName = "Testing Class";

    @Autowired
    public ClassControllerTests(MockMvc mockMvc, @Value("${testingUser}") String testingUser) {
        this.mockMvc = mockMvc;
        this.testingUser = testingUser;
        // Add assignment
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setAssignmentName(testAssignmentName);
        assignmentDto.setClassName(testClassName);
        assignmentDto.setAssignmentWeight(.5);
        assignmentDto.setAssignmentScore(100);

        try{
            System.out.println(testingUser);
            mockMvc.perform(post("/api/assignment")
                            .with(user(testingUser))
                            .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(assignmentDto))).andExpect(status().isCreated());
        } catch (Exception ex){
            throw new RuntimeException("Test setup should not throw exception", ex);
        }
    }

    @Test
    void getClassShouldReturnExpectedClass(){
        try{
            mockMvc.perform(get(endpoint)
                            .with(user(testingUser)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(String.format("[{\"className\": \"%s\"}]", testClassName)));
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
