package com.app.controle_financeiro.infra.config;

import com.app.controle_financeiro.application.implementations.*;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.*;
import com.app.controle_financeiro.infra.persistence.repository.ITransactionRepositoryJpa;
import com.app.controle_financeiro.infra.persistence.repository.IUserRepositoryJpa;
import com.app.controle_financeiro.infra.persistence.repository.TransactionRepositoryImpl;
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
    public IDeleteUser deleteUser(IUserRepository userRepository, ITransactionRepository transactionRepository){
        return new DeleteUserImpl(userRepository, transactionRepository);
    }

    @Bean
    public IFindUserById findById(IUserRepository userRepository){
        return new FindUserByIdImpl(userRepository);
    }

    @Bean
    public IFindUserByEmail findByEmail(IUserRepository userRepository){
        return new FindUserByEmailImpl(userRepository);
    }

    @Bean
    public IFindUserByTelegramId findByTelegramId(IUserRepository userRepository){
        return new FindUserByTelegramIdImpl(userRepository);
    }

    @Bean
    public IUserRepository userRepository(IUserRepositoryJpa jpa) {
        return new UserRepositoryImpl(jpa);
    }

    @Bean
    public ICreateTransaction createTransaction(ITransactionRepository transactionRepository, IUserRepository userRepository){
        return new CreateTransactionImpl(transactionRepository, userRepository);
    }

    @Bean
    public ISaveTransaction saveTransaction(ITransactionRepository transactionRepository, IUserRepository userRepository){
        return new SaveTransactionImpl(transactionRepository, userRepository);
    }

    @Bean
    public IDeleteTransaction deleteTransaction(ITransactionRepository transactionRepository){
        return new DeleteTransactionImpl(transactionRepository);
    }

    @Bean
    public IDeleteAllTransactions deleteAllTransactions(ITransactionRepository transactionRepository, IUserRepository userRepository){
        return new DeleteAllTransactionsImpl(transactionRepository, userRepository);
    }

    @Bean
    public ITransactionRepository transactionRepository(ITransactionRepositoryJpa jpa, IUserRepositoryJpa userJpa) {
        return new TransactionRepositoryImpl(jpa, userJpa);
    }
}
