package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "profiles")
public class Profiles {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    @Column(name = "name_profile", nullable = false, length = 25)
    @NotBlank(message = "profile name is void")
    private String nameProfile;
    private String avatar;
    private Timestamp lastAccess;

    //relations 1:1 with Children
    @OneToOne(targetEntity = Children.class, fetch = FetchType.EAGER)
    private Children children;
}
