package com.pipejfdv.MCSAuth.MCSAuth.View.Controller;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.UserCredentials;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
* REST controller that exposes auth endpoints for login, token refresh, logout, and token deletion.
*/
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController implements UserContractFM.View {
    private final UserContractFM.Presenter presenter;

    public AuthController(UserContractFM.Presenter presenter) {
        this.presenter = presenter;
    }
    /*
    * Authenticates the user and returns a JWT access token with refresh token
    * @Params user UserCredentials the login credentials from request body
    * @Return ResponseEntity UserFMDataOK containing AuthResponse with tokens
    */
    @PostMapping("/login")
    @Override
    public ResponseEntity<UserFMDataOK<AuthResponse>> Login(@RequestBody UserCredentials user) {
        AuthResponse response = presenter.authenticate(user);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Login exitoso",
                response,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Issues a new access token when the refresh token is provided in the Authorization header
    * @Params authHeader String the Authorization header containing Bearer refresh token
    * @Return ResponseEntity UserFMDataOK containing the new AuthResponse
    */
    @PostMapping("/refresh")
    @Override
    public ResponseEntity<UserFMDataOK<AuthResponse>> specialRequest(@RequestHeader("Authorization") String authHeader) {
        AuthResponse response = presenter.confirmAuthToAccessToken(authHeader);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Allow access",
                response,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Revokes all active tokens for the user and performs logout
    * @Params authHeader String the Authorization header containing Bearer token
    * @Return ResponseEntity UserFMDataOK confirming logout success
    */
    @PostMapping("/logout")
    public ResponseEntity<UserFMDataOK<AuthResponse>> logoutRequest(@RequestHeader("Authorization") String authHeader){
        AuthResponse response = presenter.logout(authHeader);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Logout success",
                response,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Deletes all token records for a user when the user is deleted from MCSUsersFM
    * @Params id UUID the user ID to delete tokens for
    * @Return ResponseEntity UserFMDataOK confirming deletion
    */
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<UserFMDataOK<AuthResponse>> deletedTokenInformation(@PathVariable UUID id){
        AuthResponse answer = presenter.deletedToken(id);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Information successfully deleted",
                answer,
                HttpStatus.OK.value()
        ));
    }
}