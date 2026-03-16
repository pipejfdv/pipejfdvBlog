package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private UUID id;
    @NotBlank(message = "the user is void")
    @Column(length = 25)
    private String username;
    @NotBlank(message = "the email is void")
    @Column (name = "email", unique = true, nullable = false)
    private String email;
    @NotBlank(message = "error with the password")
    @Column (name = "password", nullable = false)
    private String password;

    // relation 1:1 with User
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Guardian guardian;

    // relation N:1 with Account-Type
    @ManyToOne(targetEntity = AccountType.class, fetch = FetchType.LAZY)
    private AccountType accountType;
}
