package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

/*
* Public DTO for transferring children data without sensitive document fields
*/
@Getter
public class ChildrenPublicDTO {
    private UUID id;
    private String names;
    private String lastName;
    private Integer age;
    private String tceClassification;

    /*
    * Creates a ChildrenPublicDTO with calculated age from birth date
    * @Param id the child UUID
    * @Param names the child first name
    * @Param lastName the child last name
    * @Param birthDate the child birth date
    * @Param tceClassification the TCE classification name
    */
    public ChildrenPublicDTO(UUID id, String names, String lastName, LocalDate birthDate, String tceClassification) {
        this.id = id;
        this.names = names;
        this.lastName = lastName;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
        this.tceClassification = tceClassification;
    }
}
