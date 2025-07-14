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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean
    public ICreateUser createUser(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        return new CreateUserImpl(userRepository, passwordEncoder);
    }

    @Bean
    public ISaveUser saveUser(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return new SaveUserImpl(userRepository, passwordEncoder);
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

    @Bean
    public IFindTransactionById findTransactionById(ITransactionRepository transactionRepository){
        return new FindTransactionByIdImpl(transactionRepository);
    }

    @Bean
    public IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod(ITransactionRepository transactionRepository, IUserRepository userRepository){
        return new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
