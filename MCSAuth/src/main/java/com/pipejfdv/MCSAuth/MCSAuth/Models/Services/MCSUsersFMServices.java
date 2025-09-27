package com.pipejfdv.MCSAuth.MCSAuth.Models.Services;

import com.pipejfdv.MCSAuth.MCSAuth.ComunicationMCS.FunnyMindDB;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.UserNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import feign.FeignException;
import org.springframework.stereotype.Service;


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
}
