package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

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
    @Column(length = 36)
    private UUID id;
    @NotBlank
    @Column (name = "email", unique = true, nullable = false)
    private String email;
    @NotBlank
    @Column (name = "password", nullable = false)
    private String password;
    // relation N:1 with Account-Type
    @ManyToOne(targetEntity = AccountType.class, fetch = FetchType.LAZY)
    private AccountType accountType;
}
