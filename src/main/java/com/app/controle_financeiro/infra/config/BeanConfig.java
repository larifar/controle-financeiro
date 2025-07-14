package com.app.controle_financeiro.infra.config;

import com.app.controle_financeiro.application.implementations.CreateUserImpl;
import com.app.controle_financeiro.application.implementations.SaveUserImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ICreateUser;
import com.app.controle_financeiro.application.useCases.ISaveUser;
import com.app.controle_financeiro.infra.persistence.repository.IUserRepositoryJpa;
import com.app.controle_financeiro.infra.persistence.repository.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ICreateUser createUser(IUserRepository userRepository){
        return new CreateUserImpl(userRepository);
    }

    @Bean
    public ISaveUser saveUser(IUserRepository userRepository) {
        return new SaveUserImpl(userRepository);
    }

    @Bean
    public IUserRepository userRepository(IUserRepositoryJpa jpa) {
        return new UserRepositoryImpl(jpa);
    }
}
