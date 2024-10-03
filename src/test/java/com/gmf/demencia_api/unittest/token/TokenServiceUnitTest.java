package com.gmf.demencia_api.unittest.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.services.TokenService;

@SpringBootTest
public class TokenServiceUnitTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private User user;

    @Value("${api.security.token.secret}")
    private String secret = "mySecret"; // Defina um valor padrão para o teste

    @BeforeEach
    public void setUp() {
        // Configure o usuário mock
        when(user.getLogin()).thenReturn("testUser");
        tokenService.setSecret(secret); // Atribua o valor do secret
    }

    @Test
    public void testGenerateToken_ReturnsToken() {
        String token = tokenService.generateToken(user);
        
        assertEquals("testUser", tokenService.validateToken(token)); // Valida que o token contém o login do usuário
    }

    @Test
    public void testGenerateToken_JWTCreationException() {
        // Simulando uma falha ao gerar o token
        when(user.getLogin()).thenThrow(new RuntimeException("Mocked Exception"));

        assertThrows(RuntimeException.class, () -> {
            tokenService.generateToken(user);
        });
    }

    @Test
    public void testValidateToken_ValidToken_ReturnsSubject() {
        String token = tokenService.generateToken(user);
        
        String subject = tokenService.validateToken(token);
        assertEquals("testUser", subject);
    }

    @Test
    public void testValidateToken_InvalidToken_ReturnsEmptyString() {
        String invalidToken = "invalidToken";
        
        String subject = tokenService.validateToken(invalidToken);
        assertEquals("", subject);
    }

    @Test
    public void testValidateToken_JWTVerificationException() {
        // Simulando uma falha ao validar o token
        String invalidToken = "invalidToken"; // Um token que não pode ser validado

        when(user.getLogin()).thenThrow(new JWTVerificationException("Mocked Exception"));

        String subject = tokenService.validateToken(invalidToken);
        assertEquals("", subject);
    }
}
