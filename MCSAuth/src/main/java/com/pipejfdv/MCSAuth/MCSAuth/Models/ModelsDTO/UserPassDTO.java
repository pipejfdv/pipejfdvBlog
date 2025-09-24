package com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* Create reference object UserFM
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPassDTO {
    private String username;
    private String email;
    private String password;
    private String typeOfAccount;
}