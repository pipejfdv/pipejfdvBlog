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
    * This object is the medium between Model and view
    * and assign token after to validate credentials of user
    * @Params UserCredentials username
    * @Return object AuthResponse
    * @Throw TokenNotSavedInDBException
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
    Authorization for access sensible information
     */
    public AuthResponse confirmAuthToAccessToken(String token) {
        return authService.refreshTokenForEspecialPetition(token);
    }
    /*
    * Close section and remove authorization token
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
    *  Is responsible for sending the token to the model, so that it searches the database and deletes the user's token
    *  @Param UUID id - ID of user
    *  @Return AuthResponse - response HTTP
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
