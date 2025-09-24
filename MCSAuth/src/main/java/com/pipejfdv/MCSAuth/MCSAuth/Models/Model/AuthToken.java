package com.pipejfdv.MCSAuth.MCSAuth.Models.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class AuthToken {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR) //specify type data in database
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private UUID id;
    private String token;
    private boolean revoked;
    private boolean expired;
    // relation 1:1 with User in MCSUsersFM
    @Column(name = "userId", nullable = false)
    private UUID userId;
}
