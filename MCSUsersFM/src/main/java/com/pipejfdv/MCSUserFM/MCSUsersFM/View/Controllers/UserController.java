package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Config.JwtUtils;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.UserDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.UserPassDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.AccountTypePresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.UserPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j

@RestController
@RequestMapping("/funnyMind")
public class UserController implements UserContract.View {
    private final UserPresenter userPresenter;
    private final AccountTypePresenter accountTypePresenter;
    private final JwtUtils jwtUtils;

    public UserController(UserPresenter userPresenter, JwtUtils jwtUtils, AccountTypePresenter accountTypePresenter) {
        this.userPresenter = userPresenter;
        this.jwtUtils = jwtUtils;
        this.accountTypePresenter = accountTypePresenter;
    }

    @GetMapping("/User/userData")
    @Override
/*
* Gets user data and returns it as public DTO
* @Params idUser UUID of the user to search (optional)
* @Params authentication Spring Security authentication object
* @Return ResponseEntity with user data
*/
public ResponseEntity<ApiResponseOK<UserDTO>> showUser(
            @RequestParam(value = "id", required = false) String idUser,
            Authentication authentication)
    {
        UUID targetId = (idUser == null || idUser.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(idUser);

        User user = userPresenter.readyUser(targetId);
        UserDTO userDTO = new UserDTO(user.getId(),user.getUsername(), user.getEmail(), user.getAccountType().getName());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user data",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Creates a new user with the specified account type
    * @Params user User object with registration data
    * @Params typeOfAccount Account type name (e.g. DemoUser, PremiumUser)
    * @Return ResponseEntity with created user data
    */
    @PostMapping("/User/create/{typeOfAccount}")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showCreateUser(@RequestBody User user, @PathVariable(required = true) String typeOfAccount) {
        AccountType accountType = accountTypePresenter.getAccountType(null, typeOfAccount);
        User newUser = userPresenter.readyToCreateUser(user, accountType.getName());
        UserDTO userDTO = new UserDTO(newUser.getId(),newUser.getUsername(), newUser.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user created",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Deletes a user by ID or from authentication token
    * @Params idUser UUID of the user to delete (optional)
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with deleted user data
    */
    @DeleteMapping("/User/delete")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showDeleteUser(
            @RequestParam(value = "id", required = false) String idUser,
            Authentication authentication) {

        UUID targetId = (idUser == null || idUser.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(idUser);

        User deleteUser = userPresenter.readyUser(targetId);
        UserDTO userDTO = new UserDTO(deleteUser.getId(),deleteUser.getUsername(), deleteUser.getEmail());
        userPresenter.readyToDeleteUser(targetId);

        ApiResponseOK<UserDTO> response = new ApiResponseOK<>(
                "user deleted",
                userDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    /*
    * Returns a list of all registered users
    * @Return ResponseEntity with list of user DTOs
    */
    @GetMapping("/User/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<UserDTO>>> showAllUsers() {
        List<UserDTO> listDTO = userPresenter.UsersList().stream()
                .map(user -> new UserDTO(user.getId(),user.getUsername(), user.getEmail(), user.getAccountType().getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user list",
                listDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Updates an existing user's information
    * @Params idUser UUID of the user to update (optional)
    * @Params InfoUserUpdate User object with updated fields
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with updated user data
    */
    @PutMapping("/User/update")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showEditUser(
            @RequestParam(value = "id", required = false) String idUser,
            @RequestBody User InfoUserUpdate,
            Authentication authentication)
    {
        UUID targetId = (idUser == null || idUser.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(idUser);

        User userUpdate = userPresenter.readyUpdateUser(targetId, InfoUserUpdate);
        UserDTO userDTO = new UserDTO(userUpdate.getId(),userUpdate.getUsername(), userUpdate.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user updated",
                userDTO,
                HttpStatus.OK.value()
        ));
    }
    /*
    * Checks if a user exists related to guardians by ID
    * @Params idUser UUID of the user to check
    * @Return ResponseEntity with boolean value
    */
    @Override
    @GetMapping("/User/exists/{idUser}")
    public ResponseEntity<ApiResponseOK<Boolean>> userExists(@PathVariable UUID idUser) {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user exists",
                userPresenter.userExists(idUser),
                HttpStatus.OK.value()
        ));
    }

    /*
    * Sends user authentication data (including password) to MCSAuth microservice
    * @Params username Username to search
    * @Return UserPassDTO with id, username, password and account type
    */
    @GetMapping("/User/Auth/info/{username}")
    public UserPassDTO showViewUserInfoAuth(@PathVariable String username){
        User userAuth = userPresenter.readyUserInfoAuth(username);
        return new UserPassDTO(userAuth.getId(),
                userAuth.getUsername(),
                userAuth.getPassword(),
                userAuth.getAccountType().getName());
    }
}
