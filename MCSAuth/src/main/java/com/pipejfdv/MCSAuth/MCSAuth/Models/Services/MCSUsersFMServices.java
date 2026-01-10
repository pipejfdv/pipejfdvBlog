package com.pipejfdv.MCSAuth.MCSAuth.Models.Services;

import com.pipejfdv.MCSAuth.MCSAuth.ComunicationMCS.FunnyMindDB;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.NotCompletedUpdateTokenException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.NotFoundTokenException;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.UserNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MCSUsersFMServices implements UserContractFM.Model {
    private final FunnyMindDB funnyMindDB;

    public MCSUsersFMServices(FunnyMindDB funnyMindDB) {
        this.funnyMindDB = funnyMindDB;
    }
    /*
    *   This object is used to query data from MCSUsersFM
    *   @Params UUID idUSer
    *   @Return object UserDTO
    */
    @Override
    public UserPassDTO getCredentialsUser(String username) {
        try {
            return funnyMindDB.getCredentialsUser(username); // query FeignClient
        }
        catch (FeignException.NotFound ex) {
            throw new UserNotFoundException(username);
        }
    }

    /*
    * Those methods are give here because contract with interface is solicited but no necessary in this model
    */
    @Override
    public AuthResponse refreshTokenForEspecialPetition(String authHeader) {
        return null;
    }
    @Override
    public AuthToken findByToken(String token) throws NotFoundTokenException {
        return null;
    }
    @Override
    public Boolean updateToken(AuthToken authToken) throws NotCompletedUpdateTokenException {
        return null;
    }
    @Override
    public Boolean deletedRegistryTokenUser(UUID id) throws UserNotFoundException {
        return null;
    }
}
