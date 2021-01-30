package com.zhuravlov.repairagency.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserEntity {

    BigDecimal amount = BigDecimal.valueOf(0);
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
    @Column(unique = true)
    @NotBlank(message = "email is required")
    @NonNull
    private String email;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private Set<RoleEntity> roles;

    @NonNull
    @NotBlank(message = "password is required")
    @EqualsAndHashCode.Exclude
    private String password;

}
