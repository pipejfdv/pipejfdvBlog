package com.pipejfdv.MCSAuth.MCSAuth.Models.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentials {
    /*
    structure of data in request Login in Controller
     */
    private String username;
    private String password;
}
