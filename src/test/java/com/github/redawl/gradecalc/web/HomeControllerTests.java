package com.github.redawl.gradecalc.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class HomeControllerTests {

    private final String testingUser;

    private final MockMvc mockMvc;

    @Autowired
    public HomeControllerTests(MockMvc mockMvc, @Value("${testingUser}") String testingUser) {
        this.mockMvc = mockMvc;
        this.testingUser = testingUser;
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/class", "/error"})
    void pathsShouldAllReturnOk(String path){
        try {
            mockMvc.perform(get(path)
                    .with(user(testingUser)))
                    .andExpect(status().isOk());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"/none", "/never-existed"})
    void nonExistentPathsShouldReturnNotFound(String path){
        try {
            mockMvc.perform(get(path)
                            .with(user(testingUser)))
                    .andExpect(status().isNotFound());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception", ex);
        }
    }
}
