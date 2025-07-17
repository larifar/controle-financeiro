package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.CreateUserImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ICreateUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserAlreadyExistsException;
import com.app.controle_financeiro.domain.exceptions.UserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CreateUserUseCaseTest {
    @Mock
    IUserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldNotCreateUserWhitoutTelegramId(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setName("name");
        user.setEmail("email");
        user.setPassword("password");

        assertThrows(UserException.class, ()-> createUser.save(user));
    }

    @Test
    void shouldNotCreateUserWhitoutName(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setPassword("password");

        assertThrows(UserException.class, ()-> createUser.save(user));
    }

    @Test
    void shouldNotCreateUserWhitoutPassword(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setName("name");

        assertThrows(UserException.class, ()-> createUser.save(user));
    }

    @Test
    void shouldNotCreateUserWhitoutEmail(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setPassword("password");
        user.setName("name");

        assertThrows(UserException.class, ()-> createUser.save(user));
    }

    @Test
    void shouldNotCreateUserWhenTelegramIdAlreadyRegister(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");
        when(userRepository.findByTelegramId(123L)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserAlreadyExistsException.class, ()-> createUser.save(user));
    }

    @Test
    void shoudlNotCreateUserWhenEmailAlreadyRegister(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserAlreadyExistsException.class, ()-> createUser.save(user));
    }

    @Test
    void shouldCreateUserWhenValid(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");
        when(userRepository.findByTelegramId(123L)).thenReturn(Optional.empty());
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = createUser.save(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void shouldEncryptPasswordBeforeCreateUser(){
        User user = new User();
        ICreateUser createUser = new CreateUserImpl(userRepository, passwordEncoder);

        user.setTelegramId(123L);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");
        when(userRepository.findByTelegramId(123L)).thenReturn(Optional.empty());
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = createUser.save(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
        assertNotEquals("password", result.getPassword());
        assertTrue(passwordEncoder.matches("password", result.getPassword()));
    }
}
