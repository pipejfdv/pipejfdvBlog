package com.pipejfdv.MCSAuth.MCSAuth.Models.Services;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    private AuthTokenRepository authTokenRepository;
    /*
     * This object is use for save AuthToken in database MCSAuth
     * @Params UserPassDTO user
     * @Params String token
     * @Return boolean Confirmation to save object
     */
    public boolean addAuthToken(UserPassDTO user, String token) {
        AuthToken authToken = new AuthToken(
                UUID.randomUUID(),
                token,
                AuthToken.TokenType.BEARER,
                false,
                false,
                user.getIdUser()
        );
        AuthToken tokenSave = authTokenRepository.save(authToken);
        return tokenSave.getId() != null; /*confirmation save*/
    }
    /*
    This method is in charge remove all tokens the user when exit the program
     */
    public void revokeAllUserTokens(UserPassDTO user) {
        List<AuthToken> validTokensUser = authTokenRepository.findByUserIdFM(user.getIdUser());
        if(!validTokensUser.isEmpty()) {
            for (AuthToken authToken : validTokensUser) {
                authToken.setRevoked(true);
                authToken.setExpired(true);
            }
            authTokenRepository.saveAll(validTokensUser);
        }
    }
}
