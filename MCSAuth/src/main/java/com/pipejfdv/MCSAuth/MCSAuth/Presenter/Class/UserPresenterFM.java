package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Class;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtUtil;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.InvalidBearerTokenException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenNotSavedInDBException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.UserNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.UserCredentials;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.AuthService;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/*
* Presenter layer that mediates between the View (AuthController) and Model (AuthService/MCSUsersFMServices).
* Handles authentication logic, token generation, and user session management.
*/
@Component
public class UserPresenterFM implements UserContractFM.Presenter {
    private final MCSUsersFMServices mcsUsersFMServices;
    private final AuthService authService;
    private final JwtUtil jwt;
    // interface of spring security - facilitates the authentication process
    private final AuthenticationManager authenticationManager;

    public UserPresenterFM(MCSUsersFMServices mcsUsersFMServices,
                           JwtUtil jwt,
                           AuthenticationManager authenticationManager,
                           AuthService authService) {
        this.mcsUsersFMServices = mcsUsersFMServices;
        this.jwt = jwt;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    /*
    * Authenticates user credentials and generates access and refresh tokens
    * @Params username UserCredentials the login credentials
    * @Return AuthResponse containing tokens, username, account type and expiration
    * @Throw TokenNotSavedInDBException if token could not be saved in database
    */
    @Override
    public AuthResponse authenticate(UserCredentials username) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username.getUsername(),
                        username.getPassword())
        );
        UserPassDTO userPassDTO = mcsUsersFMServices.getCredentialsUser(username.getUsername());
        String token = jwt.generateToken(userPassDTO);
        String refreshToken = jwt.generateRefreshToken(userPassDTO);
        boolean confirmation = authService.addAuthToken(userPassDTO, token);
        if(confirmation) {
            return new AuthResponse(
                    token,
                    refreshToken,
                    userPassDTO.getUsername(),
                    userPassDTO.getTypeOfAccount(),
                    LocalDateTime.now()
            );
        }
        else  {
            throw new TokenNotSavedInDBException(userPassDTO.getIdUser());
        }
    }

    /*
    * Validates the refresh token and issues a new access token for sensitive endpoints
    * @Param token String the Authorization header with Bearer refresh token
    * @Return AuthResponse containing the new access token
    */
    public AuthResponse confirmAuthToAccessToken(String token) {
        return authService.refreshTokenForEspecialPetition(token);
    }
    /*
    * Logs out the user by revoking all their active tokens
    * @Param authHeader String the Authorization header with Bearer token
    * @Return AuthResponse empty response confirming logout
    * @Throw InvalidBearerTokenException if the Authorization header is missing or invalid
    */
    @Override
    public AuthResponse logout(String authHeader) throws InvalidBearerTokenException {
        if(authHeader == null && authHeader.startsWith("Bearer ")){
            throw new InvalidBearerTokenException(authHeader);
        }
        final String jwtToken = authHeader.substring(7);
        final String user = jwt.extractUsernameForToken(jwtToken);
        if(authService.revokeAllUserTokens(mcsUsersFMServices.getCredentialsUser(user))){
            return new AuthResponse();
        }
        return null;
    }
    /*
    * Deletes all token records for a user by their ID
    * @Param id UUID the user ID
    * @Return AuthResponse empty response confirming deletion
    * @Throw UserNotFoundException if the user has no token records
    */
    @Override
    public AuthResponse deletedToken(UUID id) throws UserNotFoundException {
        boolean answer = authService.deletedRegistryTokenUser(id);
        if(answer){
            return new AuthResponse();
        }
        return null;
    }
}
