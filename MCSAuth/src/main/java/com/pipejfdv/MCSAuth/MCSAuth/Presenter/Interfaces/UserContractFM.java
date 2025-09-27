package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.UserNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenNotSavedException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.UserCredentials;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserContractFM {
    interface View{
        /*
        * @Return Pack response type personality HTTP UserFMDataOK with token that is an object AuthResponse
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> Login(UserCredentials user);
    }
    interface Presenter{
        /*
        *  to verify credentials
        */
        AuthResponse authenticate(UserCredentials user) throws TokenNotSavedException;
    }
    interface Model{
        /*
        * Search user in MCSUsersFM
         */
        UserPassDTO getCredentialsUser(String username) throws UserNotFoundException;
    }
}
