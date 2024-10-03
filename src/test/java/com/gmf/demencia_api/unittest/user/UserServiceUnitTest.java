package com.gmf.demencia_api.unittest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.domain.user.enums.UserRole;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.repositories.UserRepository;
import com.gmf.demencia_api.services.UserService;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceUnitTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindAll_ReturnsAllUsers() {
        // Mocking data
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1", "User One", UserRole.USER));
        users.add(new User("user2", "password2", "User Two", UserRole.ADMIN));

        // Mocking repository behavior
        when(userRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(users));

        // Calling the service method
        Page<User> result = userService.findAll(Pageable.unpaged());

        // Asserting the result
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testFindById_ExistingId_ReturnsUser() {
        User user = new User("1", "user1", "User One", UserRole.USER);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.findById("1");

        assertNotNull(result);
        assertEquals("User One", result.getFullName());
    }

    @Test
    public void testFindById_NonExistingId_ThrowsNotFoundException() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.findById("999");
        });
    }

    @Test
    public void testCreate_ValidUser_ReturnsCreatedUser() {
        User inputUser = new User("user3", "password3", "User Three", UserRole.USER);
        User persistedUser = new User("1", "user3", "password3", "User Three", UserRole.USER); // Criar o usuário persistido com ID

        // Mockando o comportamento de save
        when(userRepository.save(any(User.class))).thenReturn(persistedUser);

        // Chamando o método create do serviço
        User createdUser = userService.create(inputUser);

        // Asserts
        assertNotNull(createdUser); // Verifica se o usuário criado não é nulo
        assertNotNull(createdUser.getId()); // Verifica se o ID não é nulo
        assertEquals("User Three", createdUser.getFullName()); // Verifica o nome
    }

    @Test
    public void testDeleteById_ExistingId_DeletesUser() {
        when(userRepository.existsById("1")).thenReturn(true);

        userService.deleteById("1");

        // Verify that the delete method was called, if needed
    }

    @Test
    public void testDeleteById_NonExistingId_ThrowsNotFoundException() {
        when(userRepository.existsById("999")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            userService.deleteById("999");
        });
    }

    @Test
    public void testUpdate_ExistingId_ReturnsUpdatedUser() {
        User existingUser = new User("1", "user1", "User One", UserRole.USER);
        when(userRepository.findById("1")).thenReturn(Optional.of(existingUser));

        User updatedUser = new User("1", "user1_updated", "User One Updated", UserRole.ADMIN);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update("1", updatedUser);

        assertNotNull(result);
        assertEquals("User One Updated", result.getFullName());
        assertEquals(UserRole.ADMIN, result.getRole());
    }

    @Test
    public void testUpdate_NonExistingId_ThrowsNotFoundException() {
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userService.update("999", new User());
        });
    }
}
