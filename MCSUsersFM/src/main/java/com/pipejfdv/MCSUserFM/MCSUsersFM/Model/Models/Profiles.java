package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profiles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
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
