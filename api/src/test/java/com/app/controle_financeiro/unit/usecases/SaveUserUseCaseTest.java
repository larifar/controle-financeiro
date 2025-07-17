package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.SaveUserImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ISaveUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SaveUserUseCaseTest {

    @Mock
    IUserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldThrowIfIdNotPresent(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = mock(User.class);

        UserException ex = assertThrows(UserException.class, ()-> saveUser.save(user));
        assertEquals(ExceptionCodeEnum.USER04.getCode(), ex.code);
    }

    @Test
    void shouldThrowIfUserNotFound(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, ()-> saveUser.save(user));
        assertEquals(ExceptionCodeEnum.USER01.getCode(), ex.code);
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSetTelegramIdIfNotPresentAndUserExists(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);
        long telegramId = 8888L;

        User userRepo = new User();
        userRepo.setId(99L);
        userRepo.setTelegramId(telegramId);

        when(userRepository.findById(99L)).thenReturn(Optional.of(userRepo));

        saveUser.save(user);

        assertEquals(telegramId, user.getTelegramId());
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSetEmailIfNotPresentAndUserExists(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);
        long telegramId = 8888L;
        String email = "email@test.com";

        User userRepo = new User();
        userRepo.setId(99L);
        userRepo.setTelegramId(telegramId);
        userRepo.setEmail(email);

        when(userRepository.findById(99L)).thenReturn(Optional.of(userRepo));

        saveUser.save(user);

        assertEquals(email, user.getEmail());
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSetNameIfNotPresentAndUserExists(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);
        String name = "Meu Nome";

        User userRepo = new User();
        userRepo.setId(99L);
        userRepo.setName(name);

        when(userRepository.findById(99L)).thenReturn(Optional.of(userRepo));

        saveUser.save(user);

        assertEquals(name, user.getName());
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSetPasswordIfNotPresentAndUserExists(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);
        long telegramId = 8888L;
        String email = "email@test.com";
        String password = passwordEncoder.encode("senha");

        User userRepo = new User();
        userRepo.setId(99L);
        userRepo.setTelegramId(telegramId);
        userRepo.setEmail(email);
        userRepo.setPassword(password);

        when(userRepository.findById(99L)).thenReturn(Optional.of(userRepo));

        saveUser.save(user);

        assertEquals(password, user.getPassword());
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSetPasswordAndEncoderWhenSaveUser(){
        ISaveUser saveUser = new SaveUserImpl(userRepository, passwordEncoder);
        User user = new User();
        user.setId(99L);
        long telegramId = 8888L;
        String email = "email@test.com";
        String password = "senha";
        user.setPassword(password);

        User userRepo = new User();
        userRepo.setId(99L);
        userRepo.setTelegramId(telegramId);
        userRepo.setEmail(email);

        when(userRepository.findById(99L)).thenReturn(Optional.of(userRepo));

        saveUser.save(user);

        assertEquals(passwordEncoder.encode(password), user.getPassword());
        verify(userRepository).findById(99L);
        verify(userRepository).save(user);
    }
}
