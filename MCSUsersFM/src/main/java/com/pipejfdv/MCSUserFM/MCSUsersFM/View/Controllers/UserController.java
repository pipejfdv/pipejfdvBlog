package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j

@RestController
@RequestMapping("/funnyMind")
public class UserController implements UserContract.View {
    private final UserPresenter presenter;

    public UserController(UserPresenter presenter) {
        this.presenter = presenter;
    }

    @GetMapping("/User/userData/{id}")
    @Override
    /*
    *   This component take information about USER and show these data
    */
    public ResponseEntity<ApiResponseOK<UserDTO>> showUser(@PathVariable UUID id) {
        User user = presenter.readyUser(id);
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
        User newUser = presenter.readyToCreateUser(user, typeOfAccount);
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getEmail());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user created",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    @DeleteMapping("/User/delete/{id}")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showDeleteUser(
            @RequestParam(value = "id", required = false) String idUser,
            Authentication authentication) {

        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        UUID targetId = (idUser == null || idUser.isBlank())
                ? UUID.fromString(jwtAuth.getToken().getId())
                : UUID.fromString(idUser);

        User deleteUser = presenter.readyUser(targetId);
        UserDTO userDTO = new UserDTO(deleteUser.getUsername(), deleteUser.getEmail());
        ApiResponseOK response = new ApiResponseOK<>(
                "user deleted",
                userDTO,
                HttpStatus.OK.value()
        );
        presenter.readyToDeleteUser(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/User/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<UserDTO>>> showAllUsers() {
        List<UserDTO> listDTO = presenter.UsersList().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "user list",
                listDTO,
                HttpStatus.OK.value()
        ));
    }

    @PutMapping("/User/update/{id}")
    @Override
    public ResponseEntity<ApiResponseOK<UserDTO>> showEditUser(@PathVariable UUID id, @RequestBody User user) {
        User userUpdate = presenter.readyUpdateUser(id, user);
        UserDTO userDTO = new UserDTO(userUpdate.getUsername(), userUpdate.getEmail());
        ApiResponseOK response = new ApiResponseOK<>(
                "user updated",
                userDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
    /*
    This method is in charge of send information user from database at MCSAuth
    @Params String username
    @Return UserPassDTO
     */
    @GetMapping("/User/Auth/info/{username}")
    public UserPassDTO showViewUserInfoAuth(@PathVariable String username){
        User userAuth = presenter.readyUserInfoAuth(username);
        return new UserPassDTO(userAuth.getId(),
                userAuth.getUsername(),
                userAuth.getPassword(),
                userAuth.getAccountType().getName());
    }
}
