package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import java.util.UUID;

/*
* DTO for transferring account type data
*/
public class AccountTypeDTO {
    final private String nameDTO;
    private UUID id;
    /*
    * Creates an AccountTypeDTO with name only
    * @Param name the account type name
    */
    public AccountTypeDTO(String name) {
        this.nameDTO = name;
    }
    /*
    * Creates an AccountTypeDTO with name and ID
    * @Param name the account type name
    * @Param id the account type UUID
    */
    public AccountTypeDTO(String name, UUID id) {
        this.id=id;
        this.nameDTO = name;
    }
    public String getNameDTO() {
        return nameDTO;
    }
    public UUID getId() {
        return id;
    }
}
