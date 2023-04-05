package net.bumblebee.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    @Mock
    private Authentication auth;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setUp() {
        Mockito.when(auth.isAuthenticated()).thenReturn(true);
    }

    @Test
    public void testValidLogin() {
        ResponseEntity<String> response = loginController.login(null, auth);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login Success", response.getBody());
    }

    @Test
    public void testInvalidLogin() {
        ResponseEntity<String> response = loginController.login("error", null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username and/or password.", response.getBody());
    }

    @Test
    public void testNotAuthenticated() {
        Mockito.when(auth.isAuthenticated()).thenReturn(false);
        ResponseEntity<String> response = loginController.login(null, auth);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
