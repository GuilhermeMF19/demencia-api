package com.gmf.demencia_api.unittest.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.repositories.UserRepository;
import com.gmf.demencia_api.services.AuthorizationService;

public class AuthorizationServiceUnitTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("1", "testUser", "password", "Test User", null);
    }

    @Test
    public void testLoadUserByUsername_ExistingUser_ReturnsUserDetails() {
        when(userRepository.findByLogin("testUser")).thenReturn(user);

        UserDetails result = authorizationService.loadUserByUsername("testUser");

        assertEquals("testUser", result.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testLoadUserByUsername_NonExistingUser_ThrowsUsernameNotFoundException() {
        when(userRepository.findByLogin("nonExistingUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authorizationService.loadUserByUsername("nonExistingUser");
        });
    }
}
