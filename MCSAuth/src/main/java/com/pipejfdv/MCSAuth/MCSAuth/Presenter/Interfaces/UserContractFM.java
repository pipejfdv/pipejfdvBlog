package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.*;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.UserCredentials;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/*
* Defines the contract interfaces for the MVP pattern used in MCSAuth.
* Contains View, Presenter, and Model sub-interfaces for auth operations.
*/
public interface UserContractFM {
    /*
    * View interface defining HTTP endpoint contracts for auth requests
    */
    interface View{
        /*
        * Sends login request to authenticate user and receive tokens
        * @Param user UserCredentials the login credentials
        * @Return ResponseEntity containing AuthResponse with JWT tokens
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> Login(UserCredentials user);
        /*
        * Sends refresh request to get a new access token
        * @Param authHeader String the Authorization header with Bearer refresh token
        * @Return ResponseEntity containing AuthResponse with new access token
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> specialRequest(String authHeader);
        /*
        * Sends logout request to revoke all user tokens
        * @Param authHeader String the Authorization header with Bearer token
        * @Return ResponseEntity confirming logout
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> logoutRequest (String authHeader);
        /*
        * Sends request to delete all token records for a user
        * @Param id UUID the user ID
        * @Return ResponseEntity confirming deletion
        */
        ResponseEntity<UserFMDataOK<AuthResponse>> deletedTokenInformation (UUID id);
    }
    /*
    * Presenter interface defining business logic contracts for auth operations
    */
    interface Presenter{
        /*
        * Authenticates user credentials and returns tokens
        * @Param user UserCredentials the login credentials
        * @Return AuthResponse with generated tokens
        * @Throw TokenNotSavedInDBException if token persistence fails
        */
        AuthResponse authenticate(UserCredentials user) throws TokenNotSavedInDBException;
        /*
        * Validates refresh token and returns a new access token
        * @Param token String the Authorization header
        * @Return AuthResponse with new access token
        * @Throw InvalidBearerTokenException if token format is invalid
        * @Throw TokenInvalidInfoException if token info is invalid
        */
        AuthResponse confirmAuthToAccessToken(String token) throws InvalidBearerTokenException, TokenInvalidInfoException;
        /*
        * Logs out user by revoking all tokens
        * @Param autHeader String the Authorization header
        * @Return AuthResponse confirming logout
        * @Throw InvalidBearerTokenException if token format is invalid
        */
        AuthResponse logout(String autHeader) throws InvalidBearerTokenException;
        /*
        * Deletes all token information for a user
        * @Param id UUID the user ID
        * @Return AuthResponse confirming deletion
        * @Throw UserNotFoundException if user has no token records
        */
        AuthResponse deletedToken (UUID id) throws UserNotFoundException;
    }
    /*
    * Model interface defining data access contracts for auth operations
    */
    interface Model{
        /*
        * Retrieves user credentials from MCSUsersFM microservice
        * @Param username String the username to look up
        * @Return UserPassDTO the user credentials data
        * @Throw UserNotFoundException if user is not found
        */
        UserPassDTO getCredentialsUser(String username) throws UserNotFoundException;
        /*
        * Validates refresh token and generates a new access token
        * @Param authHeader String the Authorization header
        * @Return AuthResponse with new access token
        * @Throw InvalidBearerTokenException if token format is invalid
        * @Throw TokenInvalidInfoException if token info is invalid
        */
        AuthResponse refreshTokenForEspecialPetition(String authHeader) throws InvalidBearerTokenException, TokenInvalidInfoException;
        /*
        * Finds a token record by its token string
        * @Param token String the token to search for
        * @Return AuthToken the found token entity
        * @Throw NotFoundTokenException if token is not found
        */
        AuthToken findByToken(String token) throws NotFoundTokenException;
        /*
        * Updates token information in the database
        * @Param authToken AuthToken the token entity to update
        * @Return Boolean true if update was successful
        * @Throw NotCompletedUpdateTokenException if update fails
        */
        Boolean updateToken(AuthToken authToken) throws NotCompletedUpdateTokenException;
        /*
        * Deletes all token records for a user from the database
        * @Param id UUID the user ID
        * @Return Boolean true if deletion was successful
        * @Throw UserNotFoundException if user is not found
        */
        Boolean deletedRegistryTokenUser (UUID id) throws UserNotFoundException;
    }

}
