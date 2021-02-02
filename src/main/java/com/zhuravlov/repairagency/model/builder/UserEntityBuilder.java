package com.zhuravlov.repairagency.model.builder;

import com.zhuravlov.repairagency.model.entity.RoleEntity;
import com.zhuravlov.repairagency.model.entity.UserEntity;

import java.math.BigDecimal;
import java.util.Set;

public class UserEntityBuilder {
    private UserEntity userEntity;

    public UserEntityBuilder() {
        this.userEntity = new UserEntity();
    }

    public UserEntityBuilder setFirstName(String firstName) {
        userEntity.setFirstName(firstName);
        return this;
    }

    public UserEntityBuilder setLastName(String lastName) {
        userEntity.setLastName(lastName);
        return this;
    }

    public UserEntityBuilder setEmail(String email) {
        userEntity.setEmail(email);
        return this;
    }

    public UserEntityBuilder setRoles(Set<RoleEntity> roles) {
        userEntity.setRoles(roles);
        return this;
    }

    public UserEntityBuilder setPassword(String password) {
        userEntity.setPassword(password);
        return this;
    }

    public UserEntityBuilder setAmount(BigDecimal amount) {
        userEntity.setAmount(amount);
        return this;
    }

    public UserEntity build() {
        return userEntity;
    }
}
