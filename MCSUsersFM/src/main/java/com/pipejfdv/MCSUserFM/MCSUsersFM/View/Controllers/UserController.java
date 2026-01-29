package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Config.JwtUtils;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserPassDTO;
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
    *   This component take information about USER and show these data
    */
    public ResponseEntity<ApiResponseOK<UserDTO>> showUser(
            @RequestParam(value = "id", required = false) String idUser,
            Authentication authentication)
    {
        UUID targetId = (idUser == null || idUser.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(idUser);

        User user = userPresenter.readyUser(targetId);
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user data",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    @PostMapping("/User/create/{typeOfAccount}")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showCreateUser(@RequestBody User user, @PathVariable String typeOfAccount) {
        AccountType accountType = accountTypePresenter.getAccountType(null, typeOfAccount);
        User newUser = userPresenter.readyToCreateUser(user, accountType.getName());
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user created",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    @DeleteMapping("/User/delete")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showDeleteUser(
            @RequestParam(value = "id", required = false) String idUser,
            Authentication authentication) {

        UUID targetId = (idUser == null || idUser.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(idUser);

        User deleteUser = userPresenter.readyUser(targetId);
        UserDTO userDTO = new UserDTO(deleteUser.getUsername(), deleteUser.getEmail());
        userPresenter.readyToDeleteUser(targetId);

        ApiResponseOK<UserDTO> response = new ApiResponseOK<>(
                "user deleted",
                userDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/User/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<UserDTO>>> showAllUsers() {
        List<UserDTO> listDTO = userPresenter.UsersList().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user list",
                listDTO,
                HttpStatus.OK.value()
        ));
    }

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
        UserDTO userDTO = new UserDTO(userUpdate.getUsername(), userUpdate.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user updated",
                userDTO,
                HttpStatus.OK.value()
        ));
    }
    /*
    This method is in charge of send information user from database at MCSAuth
    @Params String username
    @Return UserPassDTO
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
