package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.UserPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.Responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funnyMind")
public class UserController implements UserContract.View {
    private final UserPresenter presenter;

    public UserController(UserPresenter presenter) {
        this.presenter = presenter;
    }

    @GetMapping("/User/userData/{id}")
    @Override
    public ResponseEntity<ApiResponse<UserDTO>> showUser(@PathVariable UUID id) {
        User user = presenter.readyUser(id);
        /*------ADMIN------- this function is necessary move to presenter */
        /*
        if(user.getAccountType().getName().equals("Admin")) {
            UserDTO userAdminDTO = new UserDTO(user.getUsername(), user.getEmail(), user.getAccountType());
            return ResponseEntity.ok(new ApiResponse<>(
                    "user data for admin",
                    userAdminDTO,
                    HttpStatus.OK.value()
            ));
        }*/
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(new ApiResponse<>(
                "user data",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    @PostMapping("/User/create/{typeOfAccount}")
    @Override
    public ResponseEntity<ApiResponse<UserDTO>> showCreateUser(@RequestBody User user, @PathVariable String typeOfAccount) {
        User newUser = presenter.readyToCreateUser(user, typeOfAccount);
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getEmail());
        return ResponseEntity.ok(new ApiResponse<>(
                "user created",
                userDTO,
                HttpStatus.OK.value()
        ));
    }

    @DeleteMapping("/User/delete/{id}")
    @Override
    public ResponseEntity<ApiResponse<UserDTO>> showDeleteUser(@PathVariable UUID id) {
        User deleteUser = presenter.readyUser(id);
        UserDTO userDTO = new UserDTO(deleteUser.getUsername(), deleteUser.getEmail());
        ApiResponse response = new ApiResponse<>(
                "user deleted",
                userDTO,
                HttpStatus.OK.value()
        );
        presenter.readyToDeleteUser(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/User/list")
    @Override
    public ResponseEntity<ApiResponse<List<UserDTO>>> showAllUsers() {
        List<UserDTO> listDTO = presenter.UsersList().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(
                "user list",
                listDTO,
                HttpStatus.OK.value()
        ));
    }

    @PutMapping("/User/update/{id}")
    @Override
    public ResponseEntity<ApiResponse<UserDTO>> showEditUser(@PathVariable UUID id, @RequestBody User user) {
        User userUpdate = presenter.readyUpdateUser(id, user);
        UserDTO userDTO = new UserDTO(userUpdate.getUsername(), userUpdate.getEmail());
        ApiResponse response = new ApiResponse<>(
                "user updated",
                userDTO,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
