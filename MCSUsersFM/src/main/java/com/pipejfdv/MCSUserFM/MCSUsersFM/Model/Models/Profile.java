package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column(name = "name_profile", nullable = false, length = 25)
    @NotBlank(message = "profile name is void")
    private String nameProfile;
    private LocalDateTime lastAccess;

    //relations 1:1 with Children
    @OneToOne(targetEntity = Children.class, fetch = FetchType.EAGER)
    private Children children;

    @PrePersist
    public void prePersist() {
        lastAccess = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastAccess = LocalDateTime.now();
    }

    public Profile(String nameProfile, Children children) {
        this.nameProfile = nameProfile;
        this.children = children;
    }
}
