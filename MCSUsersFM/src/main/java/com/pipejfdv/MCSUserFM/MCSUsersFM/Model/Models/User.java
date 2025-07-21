package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, updatable = false, nullable = false)
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
    @OneToOne(targetEntity = Guardian.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Guardian guardian;

    // relation N:1 with Account-Type
    @ManyToOne(targetEntity = AccountType.class, fetch = FetchType.LAZY)
    private AccountType accountType;
}
