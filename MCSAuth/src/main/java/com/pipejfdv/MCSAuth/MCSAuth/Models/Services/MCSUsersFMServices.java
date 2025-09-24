package com.pipejfdv.MCSAuth.MCSAuth.Models.Services;

import com.pipejfdv.MCSAuth.MCSAuth.ComunicationMCS.FunnyMindDB;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MCSUsersFMServices implements UserContractFM.Model {
    private final FunnyMindDB funnyMindDB;
    private final AuthTokenRepository authTokenRepository;

    public MCSUsersFMServices(FunnyMindDB funnyMindDB, AuthTokenRepository authTokenRepository) {
        this.funnyMindDB = funnyMindDB;
        this.authTokenRepository = authTokenRepository;
    }
    /*
    *   This object is used to query data from MCSUsersFM
    *   @Params UUID idUSer
    *   @Return object UserDTO
    */
    @Override
    public UserPassDTO getUser(UUID idUser) {
        try {
            return funnyMindDB.getUser(idUser); // query FeignClient
        }
        catch (FeignException.NotFound ex) {
            throw new IdNotFoundException(idUser);
        }
    }
    /*
    * This object is use for save AuthToken in database
    * @Params UUID idUser
    * @Return boolean Confirmation to save object
    */
    public boolean addAuthToken(UUID idUser, String token) {
        AuthToken authToken = new AuthToken(
                UUID.randomUUID(),
                token,
                false,
                false,
                idUser
        );
        AuthToken tokenSave = authTokenRepository.save(authToken);
        return tokenSave.getUserId() != null; /*confirmation save*/
    }
}
