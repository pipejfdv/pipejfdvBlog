package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
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
    private UUID id;
    @Column(name = "name_profile")
    private String nameProfile;
    private String avatar;
    private Timestamp lastAccess;

    //relations 1:1 with Children
    @OneToOne(targetEntity = Children.class, fetch = FetchType.EAGER)
    private Children children;
}
