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


/*
* Service that communicates with the MCSUsersFM microservice via Feign client to retrieve user credentials.
*/
@Service
public class MCSUsersFMServices implements UserContractFM.Model {
    private final FunnyMindDB funnyMindDB;

    public MCSUsersFMServices(FunnyMindDB funnyMindDB) {
        this.funnyMindDB = funnyMindDB;
    }
    /*
    * Retrieves user credentials from MCSUsersFM by username via Feign client
    * @Param username String the username to look up
    * @Return UserPassDTO the user data including password and account type
    * @Throw UserNotFoundException if the user is not found in MCSUsersFM
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
    * Placeholder implementation required by the Model interface, not used in MCSUsersFMServices
    * @Param authHeader String the Authorization header
    * @Return AuthResponse always returns null
    */
    @Override
    public AuthResponse refreshTokenForEspecialPetition(String authHeader) {
        return null;
    }
    /*
    * Placeholder implementation required by the Model interface, not used in MCSUsersFMServices
    * @Param token String the JWT token
    * @Return AuthToken always returns null
    */
    @Override
    public AuthToken findByToken(String token) throws NotFoundTokenException {
        return null;
    }
    /*
    * Placeholder implementation required by the Model interface, not used in MCSUsersFMServices
    * @Param authToken AuthToken the token entity
    * @Return Boolean always returns null
    */
    @Override
    public Boolean updateToken(AuthToken authToken) throws NotCompletedUpdateTokenException {
        return null;
    }
    /*
    * Placeholder implementation required by the Model interface, not used in MCSUsersFMServices
    * @Param id UUID the user ID
    * @Return Boolean always returns null
    */
    @Override
    public Boolean deletedRegistryTokenUser(UUID id) throws UserNotFoundException {
        return null;
    }
}
