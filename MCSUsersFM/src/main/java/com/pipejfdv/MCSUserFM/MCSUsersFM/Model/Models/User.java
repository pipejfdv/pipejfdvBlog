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
    private UUID id;
    @NotBlank
    @Column (name = "email", unique = true, nullable = false)
    private String email;
    @NotBlank
    @Column (name = "password", nullable = false)
    private String password;

    // relation 1:1 with User
    @OneToOne(targetEntity = Guardian.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Guardian guardian;

    // relation N:1 with Account-Type
    @ManyToOne(targetEntity = AccountType.class, fetch = FetchType.LAZY)
    private AccountType accountType;
}
