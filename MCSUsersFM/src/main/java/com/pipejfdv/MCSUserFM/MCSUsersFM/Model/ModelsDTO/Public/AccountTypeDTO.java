package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import java.util.UUID;

public class AccountTypeDTO {
    final private String nameDTO;
    private UUID id;
    public AccountTypeDTO(String name) {
        this.nameDTO = name;
    }
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
