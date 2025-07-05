package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_type")
public class AccountType {
    @Id
    @Column(length = 36)
    private UUID id;
    @Column (name = "nameAccount")
    private String name;
    // relation 1:N with Account-Type
    @OneToMany(targetEntity = User.class,fetch = FetchType.LAZY, mappedBy = "accountType")
    private List<User> users;
}
