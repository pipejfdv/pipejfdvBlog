package com.pipejfdv.MCSAuth.MCSAuth.Presenter.Class;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtUtil;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.TokenNotSavedException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserPresenterFM implements UserContractFM.Presenter {
    private final MCSUsersFMServices mcsUsersFMServices;
    private final JwtUtil jwt;

    public UserPresenterFM(MCSUsersFMServices mcsUsersFMServices,  JwtUtil jwt) {
        this.mcsUsersFMServices = mcsUsersFMServices;
        this.jwt = jwt;
    }

    /*
    * This object is the medium between Model and view
    * and assign token
    * @Params UUID idUser
    * @Return object AuthResponse
    */
    @Override
    public AuthResponse authenticate(UUID idUser) {
        UserPassDTO user = mcsUsersFMServices.getUser(idUser);
        String token = jwt.generateToken(user);
        boolean confirmation = mcsUsersFMServices.addAuthToken(idUser, token);
        if(confirmation) {
            return new AuthResponse(
                    token,
                    user.getUsername(),
                    user.getTypeOfAccount(),
                    LocalDateTime.now()
            );
        }
        else  {
            throw new TokenNotSavedException(idUser);
        }
    }
}
