package com.zhuravlov.repairagency.model.entity;

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

    @NotBlank(message = "{registration.errorFirstName}")
    @NonNull
    private String firstName;

    @NotBlank(message = "{registration.errorLastName}")
    @NonNull
    private String lastName;

    @NotBlank(message = "{registration.errorEmailFormat}")
    @Column(unique = true)
    @NotBlank(message = "{registration.errorEmailBlank}")
    @NonNull
    private String email;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private Set<RoleEntity> roles;

    @NonNull
    @NotBlank(message = "{registration.errorPassword}")
    @EqualsAndHashCode.Exclude
    private String password;

}
