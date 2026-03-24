package com.process.api.application.mapper;

import com.process.api.domain.model.UserModel;
import com.process.api.infrastructure.persistence.entity.User;

public class UserMapper {

    public static UserModel toDomain(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    public static User toEntity(UserModel model) {
        return User.builder()
                .id(model.getId())
                .email(model.getEmail())
                .password(model.getPassword())
                .build();
    }
}