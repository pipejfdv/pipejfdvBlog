package com.pipejfdv.MCSAuth.MCSAuth.Models.Services;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtUtil;
import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.*;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService implements UserContractFM.Model {
    private final AuthTokenRepository authTokenRepository;
    private final JwtUtil jwtUtil;
    private final MCSUsersFMServices mcsUsersFMServices;

    public AuthService(AuthTokenRepository authTokenRepository,
                       JwtUtil jwtUtil,
                       MCSUsersFMServices mcsUsersFMServices) {
        this.authTokenRepository = authTokenRepository;
        this.jwtUtil = jwtUtil;
        this.mcsUsersFMServices = mcsUsersFMServices;
    }

    /*
     * This object is use for save AuthToken in database MCSAuth
     * @Params UserPassDTO user
     * @Params String token
     * @Return boolean Confirmation to save object
     */
    public boolean addAuthToken(UserPassDTO user, String token) throws TokenNotSavedInDBException{
        AuthToken authToken = new AuthToken(
                UUID.randomUUID(),
                token,
                AuthToken.TokenType.BEARER,
                false,
                false,
                user.getIdUser()
        );
        AuthToken tokenSave = authTokenRepository.save(authToken);
        if(tokenSave.getId() == null){
            throw new TokenNotSavedInDBException(user.getIdUser());
        }
        return true;
    }
    /*
    This method is in charge remove all tokens the user when exit the program
     */
    public boolean revokeAllUserTokens(UserPassDTO user) throws UserNotFoundException, NotCompletedUpdateTokenException {
        List<AuthToken> validTokensUser = authTokenRepository.findByUserIdFM(user.getIdUser());
        if(validTokensUser == null){
            throw new UserNotFoundException(user.getUsername());
        }
        if(!validTokensUser.isEmpty()) {
            for (AuthToken authToken : validTokensUser) {
                authToken.setRevoked(true);
                authToken.setExpired(true);
            }
            List<AuthToken> updatedTokens =authTokenRepository.saveAll(validTokensUser);
            if(updatedTokens.size() != validTokensUser.size()){
                throw new NotCompletedUpdateTokenException();
            }
            // confirmation of token removal
            boolean allRevoked = updatedTokens.stream() .allMatch(t -> t.isRevoked() && t.isExpired());

            if(!allRevoked){
                throw new IllegalStateException("Something token not change correct");
            }
        }
        return true;
    }
    /*
     *  This method is used to verification is user have access to specific information
     *  @Param String authHeader - header token with info
     *  @Return AuthResponse
     *  @Throw InvalidBearerTokenException
     */
    public AuthResponse refreshTokenForEspecialPetition(String authHeader) {
        if (authHeader == null && !authHeader.startsWith("Bearer ")) {
            throw new InvalidBearerTokenException(authHeader);
        }
        /* Token Extraction */
        final String refreshToken = authHeader.substring("Bearer ".length());
        /* Username extraction of refreshToken */
        final String username = jwtUtil.extractUsernameForToken(refreshToken);

        if (username == null) {
            throw new TokenInvalidInfoException(authHeader);
        }

        /* Validation token and user */
        final UserPassDTO user = mcsUsersFMServices.getCredentialsUser(username);
        if(!jwtUtil.isTokenValid(refreshToken, user)) {
            throw new TokenInvalidInfoException(authHeader);
        }

        final String accessToken = jwtUtil.generateToken(user);
        revokeAllUserTokens(user);
        authTokenRepository.updateToken(accessToken, user.getIdUser());
        return new AuthResponse(accessToken, refreshToken, username, user.getTypeOfAccount(), LocalDateTime.now());
    }
    /*
    * This method is created for find token in DB MCSAuth.
    * @Param jwt token
    * @Return AuthToken
    * @Throw NotFoundTokenException
    */
    @Override
    public AuthToken findByToken(String token) throws NotFoundTokenException {
        AuthToken authToken = authTokenRepository.findByToken(token);
        if(authToken.getId() == null){
            throw new NotFoundTokenException(token);
        }
        return authToken;
    }
    /*
    * Update information for Token en DB MCSAuth
    */
    @Override
    public Boolean updateToken(AuthToken authToken) throws NotCompletedUpdateTokenException {
        AuthToken a = authTokenRepository.save(authToken);
        if(a.getId() == null){
            throw new NotCompletedUpdateTokenException();
        }
        return true;
    }

    /*
    * This method is give here because contract with interface is solicited but no necessary in this model
    * */
    @Override
    public UserPassDTO getCredentialsUser(String username) throws UserNotFoundException {
        return null;
    }
}
