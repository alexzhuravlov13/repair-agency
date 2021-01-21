package com.zhuravlov.repairagency.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer userId;

    @NotBlank(message = "first name is required")
    @NonNull
    private String firstName;

    @NotBlank(message = "last name is required")
    @NonNull
    private String lastName;

    @Email(message = "invalid email format")
    @NotBlank(message = "email is required")
    @NonNull
    private String email;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private Set<RoleEntity> roles;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String password;

    @Transient
    @EqualsAndHashCode.Exclude
    private String passwordConfirm;


}
