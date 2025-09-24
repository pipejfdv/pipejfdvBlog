package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenNotSavedException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import feign.FeignException;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserContractFM {
    interface View{
        /*
        * @Return Pack response type personality HTTP UserFMDataOK with token that is an object AuthResponse
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> showLogin(UUID idUser);
    }
    interface Presenter{
        /*
        *  to verify credentials
        */
        AuthResponse authenticate(UUID idUser) throws TokenNotSavedException;
    }
    interface Model{
        /*
        * Search user in MCSUsersFM
         */
        UserPassDTO getUser(UUID idUser) throws IdNotFoundException;
    }
}
