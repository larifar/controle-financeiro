package com.app.controle_financeiro.infra.mapper;

import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.infra.persistence.entities.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity entity){
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setName(entity.getName());
        user.setPassword(entity.getPassword());
        user.setTelegramId(entity.getTelegramId());
        return user;
    }

    public static UserEntity toEntity(User domain){
        return new UserEntity(
                domain.getId(),
                domain.getTelegramId(),
                domain.getName(),
                domain.getEmail(),
                domain.getPassword()
        );
    }
}
