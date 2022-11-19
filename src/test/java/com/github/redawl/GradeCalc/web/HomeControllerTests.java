package com.github.redawl.GradeCalc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class HomeControllerTests {
    private final MockMvc mockMvc;

    @Autowired
    public HomeControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/class", "/error"})
    void pathsShouldAllReturnOk(String path){
        try {
            mockMvc.perform(get(path)).andExpect(status().isOk());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"/none", "/never-existed"})
    void nonExistentPathsShouldReturnNotFound(String path){
        try {
            mockMvc.perform(get(path)).andExpect(status().isNotFound());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
