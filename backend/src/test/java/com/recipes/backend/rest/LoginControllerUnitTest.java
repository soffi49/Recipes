package com.recipes.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.backend.bizz.login.LoginService;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.rest.domain.LoginRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ExtendWith(MockitoExtension.class)
class LoginControllerUnitTest
{

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final LoginRest USER = new LoginRest("USER", "PASSWD");
    private static final String TOKEN = "TEST_TOKEN";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginService loginService;

    @Test
    @DisplayName("Log in with correct credentials")
    void shouldCorrectlyLogin() throws Exception
    {
        when(loginService.loginToSystem(USER)).thenReturn(TOKEN);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(USER)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(TOKEN)));
    }

    @Test
    @DisplayName("Log in with incorrect credentials")
    void shouldResponseForbiddenGivenWrongUser() throws Exception
    {
        when(loginService.loginToSystem(USER)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(USER)))
                .andExpect(status().isForbidden());
    }
}