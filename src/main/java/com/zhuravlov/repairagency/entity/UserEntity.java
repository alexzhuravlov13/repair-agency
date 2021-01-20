package com.zhuravlov.repairagency.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @EqualsAndHashCode.Include
    private int userId;

    @NotBlank(message = "first name is required")
    @Column(name = "first_Name")
    @NonNull
    private String firstName;

    @NotBlank(message = "last name is required")
    @Column(name = "last_Name")
    @NonNull
    private String lastName;


    @Email(message = "invalid email format")
    @Column(name = "email", unique = true)
    @NotBlank(message = "email is required")
    @NonNull
    @EqualsAndHashCode.Include
    private String email;

    @ManyToMany
    private Set<RoleEntity> roles;

    @NonNull
    private String password;

    @Transient
    private String passwordConfirm;


}
