package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.UsernameAuthException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface UserContract {
    interface View{
        ResponseEntity<ApiResponseOK<UserDTO>> showUser(UUID id);
        ResponseEntity<ApiResponseOK<UserDTO>> showCreateUser(User user, String typeOfAccount);
        ResponseEntity<ApiResponseOK<UserDTO>> showDeleteUser(String idSearch, Authentication authentication);
        ResponseEntity<ApiResponseOK<List<UserDTO>>> showAllUsers();
        ResponseEntity<ApiResponseOK<UserDTO>> showEditUser(UUID id, User user);

        /*
        Methods for MCSAuth in Presenter
         */
        UserPassDTO showViewUserInfoAuth(String username);
    }

    interface Presenter{
        User readyUser(UUID idAccount) throws IdNotFoundException;
        List<User> UsersList();
        void readyToDeleteUser(UUID idAccount) throws IdNotFoundException;
        User readyToCreateUser(User user, String typeOfAccount) throws DuplicateElementException;
        User readyUpdateUser(UUID idAccount, User user) throws IdNotFoundException;

        /*
        Methods for MCSAuth in Presenter
         */
        User readyUserInfoAuth (String username) throws UsernameAuthException;
    }

    interface Model{
        User getUser(UUID id)throws IdNotFoundException;
        List<User> getUsers();
        void deleteUser(UUID id) throws IdNotFoundException;
        User createUser(User user, String typeOfAccount) throws DuplicateElementException;
        User updateUser(UUID id, User user)throws IdNotFoundException;

        /*
        Methods for MCSAuth in Model
         */
        User infoUserAuth(String username) throws UsernameAuthException;
    }
}
