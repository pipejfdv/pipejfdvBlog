package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.Responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserContract {
    interface View{
        ResponseEntity<ApiResponse<UserDTO>> showUser(UUID id);
        ResponseEntity<ApiResponse<UserDTO>> showCreateUser(User user);
        ResponseEntity<ApiResponse<UserDTO>> showDeleteUser(UUID id);
        ResponseEntity<ApiResponse<List<UserDTO>>> showAllUsers();
        ResponseEntity<ApiResponse<UserDTO>> showEditUser(UUID id, User user);
    }

    interface Presenter{
        User readyUser(UUID idAccount) throws IdNotFoundException;
        List<User> UsersList();
        void readyToDeleteUser(UUID idAccount) throws IdNotFoundException;
        User readyToCreateUser(User user) throws DuplicateElementException;
        User readyUpdateUser(UUID idAccount, User user) throws IdNotFoundException;
    }

    interface Model{
        User getUser(UUID id)throws IdNotFoundException;
        List<User> getUsers();
        void deleteUser(UUID id) throws IdNotFoundException;
        User createUser(User user) throws DuplicateElementException;
        User updateUser(UUID id, User user)throws IdNotFoundException;
    }
}
