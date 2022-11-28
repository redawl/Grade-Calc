package com.github.redawl.gradecalc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.redawl.gradecalc.user.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RegisterControllerTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String testingUser;

    private final MockMvc mockMvc;

    private final String endpoint = "/api/register";

    @Autowired
    public RegisterControllerTests(@Value("${testingUser}") String testingUser, MockMvc mockMvc) {
        this.testingUser = testingUser;
        this.mockMvc = mockMvc;

        // Add user to db
        UserDto userDto = UserDto.builder()
                .username(testingUser)
                .password("password")
                .matchingPassword("password")
                .build();
        try {
            this.mockMvc.perform(post(endpoint).content(objectMapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception ex){
            Assertions.fail("Test initialization should not throw exception");
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void userNameCannotBeNull(String username){
        UserDto userDto = UserDto.builder()
                .username(username)
                .password("password")
                .matchingPassword("password")
                .build();
        try {
            mockMvc.perform(post(endpoint).content(objectMapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception");
        }
    }

    @Test
    void registeredUserShouldReturnBadRequest(){
        UserDto userDto = UserDto.builder()
                .username(testingUser)
                .password("password")
                .matchingPassword("password")
                .build();
        try {
            mockMvc.perform(post(endpoint).content(objectMapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex) {
            Assertions.fail("Test should not throw exception");
        }
    }

    @Test
    void mismatchedPasswordsShouldReturnBadRequest(){
        UserDto userDto = UserDto.builder()
                .username("ANYTHING")
                .password("password1")
                .matchingPassword("password2")
                .build();

        try {
            mockMvc.perform(post(endpoint).content(objectMapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception ex){
            Assertions.fail("Test should not throw exception");
        }
    }
}
