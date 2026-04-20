package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Getter
public class ChildrenPublicDTO {
    private UUID id;
    private String names;
    private String lastName;
    private Integer age;
    private String tceClassification;

    public ChildrenPublicDTO(UUID id, String names, String lastName, LocalDate birthDate, String tceClassification) {
        this.id = id;
        this.names = names;
        this.lastName = lastName;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
        this.tceClassification = tceClassification;
    }
}
