package com.zhuravlov.repairagency.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    BigDecimal amount = BigDecimal.valueOf(0);
    @Id
    @GeneratedValue
    private Integer userId;
    @NotBlank(message = "{registration.errorFirstName}")
    private String firstName;
    @NotBlank(message = "{registration.errorLastName}")
    private String lastName;
    @NotBlank(message = "{registration.errorEmailFormat}")
    @Column(unique = true)
    @NotBlank(message = "{registration.errorEmailBlank}")
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<RoleEntity> roles;
    @NotBlank(message = "{registration.errorPassword}")
    @EqualsAndHashCode.Exclude
    private String password;

}
