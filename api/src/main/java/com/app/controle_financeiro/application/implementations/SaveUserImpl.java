package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ISaveUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class SaveUserImpl implements ISaveUser {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SaveUserImpl(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) throws UserNotFoundException {
        if (user.getId() <= 0){
            throw new UserException("Usuário deve ter id!", ExceptionCodeEnum.USER04.getCode());
        }
        Optional<User> userExists = userRepository.findById(user.getId());
        if (userExists.isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (user.getTelegramId() <= 0 ) {
            user.setTelegramId(userExists.get().getTelegramId());
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            user.setEmail(userExists.get().getEmail());
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(userExists.get().getName());
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            user.setPassword(userExists.get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }
}
