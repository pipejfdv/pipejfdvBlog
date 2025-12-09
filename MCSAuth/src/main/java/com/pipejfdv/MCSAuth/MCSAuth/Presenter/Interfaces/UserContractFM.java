package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.InvalidBearerTokenException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenInvalidInfo;
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
        /*
        * Specify request to access sensibility information
        * @Return ResponseEntity
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> specialRequest(String authHeader);
    }
    interface Presenter{
        /*
        *  to verify credentials in MCSUsersFMServices
        */
        AuthResponse authenticate(UserCredentials user) throws TokenNotSavedException;
        /*
        * Verify if user has access to specify end point in AuthService
        */
        AuthResponse confirmAuthToAccessToken(String token) throws InvalidBearerTokenException, TokenInvalidInfo;
    }
    interface Model{
        /*
        * Search user in MCSUsersFM
         */
        UserPassDTO getCredentialsUser(String username) throws UserNotFoundException;
        /*
        * This is associated with permissions to access
        * @Param authheader is header to request
        * @Return AuthResponse new token with access
        */
        AuthResponse refreshTokenForEspecialPetition(String authHeader) throws InvalidBearerTokenException, TokenInvalidInfo;
    }

}
