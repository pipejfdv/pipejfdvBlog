package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Class;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtUtil;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenNotSavedException;
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
    * @Throw TokenNotSavedException
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
            throw new TokenNotSavedException(userPassDTO.getIdUser());
        }
    }

    /*
    Authorization for access sensible information
     */
    public AuthResponse confirmAuthToAccessToken(String token) {
        return authService.refreshTokenForEspecialPetition(token);
    }
}
