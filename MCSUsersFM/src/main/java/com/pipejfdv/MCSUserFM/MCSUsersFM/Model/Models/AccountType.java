package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "account_type")
public class AccountType {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID id;
    @Column (name = "name_account", nullable = false)
    @NotBlank(message = "name void")
    private String name;

    // relation 1:N with Account-Type
    @OneToMany(targetEntity = User.class,fetch = FetchType.LAZY, mappedBy = "accountType")
    private List<User> users;

    /*CONSTRUCTOR*/
    public AccountType(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
