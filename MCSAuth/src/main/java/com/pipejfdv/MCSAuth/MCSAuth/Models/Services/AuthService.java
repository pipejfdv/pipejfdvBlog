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

/*
* Service layer implementing the Model interface for managing authentication tokens.
* Handles token creation, revocation, refresh, and persistence in the database.
*/
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
    * Saves a new AuthToken entity in the database for the authenticated user
    * @Param user UserPassDTO the user data
    * @Param token String the JWT token to save
    * @Return boolean true if token was saved successfully
    * @Throw TokenNotSavedInDBException if the token could not be saved
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
    * Revokes all active tokens for a user by marking them as revoked and expired
    * @Param user UserPassDTO the user whose tokens to revoke
    * @Return boolean true if all tokens were revoked successfully
    * @Throw UserNotFoundException if the user has no tokens
    * @Throw NotCompletedUpdateTokenException if some tokens could not be updated
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
    * Validates the refresh token and issues a new access token for sensitive endpoints
    * @Param authHeader String the Authorization header containing the Bearer refresh token
    * @Return AuthResponse containing new access token and user info
    * @Throw InvalidBearerTokenException if the Authorization header is invalid
    * @Throw TokenInvalidInfoException if the token or user info is invalid
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
    * Finds a token record in the database by its token string
    * @Param token String the JWT token to search for
    * @Return AuthToken the token entity found in database
    * @Throw NotFoundTokenException if the token is not found
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
    * Updates an existing token entity in the database
    * @Param authToken AuthToken the token entity with updated fields
    * @Return Boolean true if the update was successful
    * @Throw NotCompletedUpdateTokenException if the update fails
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
    * Deletes the token record for a user from the database
    * @Param id UUID the user ID
    * @Return Boolean true if the token was deleted successfully
    * @Throw UserNotFoundException if no token record exists for the user
    */
    @Override
    public Boolean deletedRegistryTokenUser(UUID id) throws UserNotFoundException {
        AuthToken token = authTokenRepository.findAnOnceUser(id)
                .orElseThrow(()->new UserNotFoundException(id.toString()));
        authTokenRepository.delete(token);
        return true;
    }

    /*
    * Placeholder implementation required by the Model interface, not used in AuthService
    * @Param username String the username
    * @Return UserPassDTO always returns null
    * @Throw UserNotFoundException never thrown
    */
    @Override
    public UserPassDTO getCredentialsUser(String username) throws UserNotFoundException {
        return null;
    }
}
