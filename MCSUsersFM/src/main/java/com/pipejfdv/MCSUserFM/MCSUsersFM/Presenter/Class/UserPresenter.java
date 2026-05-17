package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.UsernameAuthException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.UserService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
* Presenter for User operations
* Acts as intermediary between controller and UserService
*/
@Component
public class UserPresenter implements UserContract.Presenter {
    private final UserService service;

    public UserPresenter(UserService service) {
        this.service = service;
    }

    /*
    * Retrieves a user by ID
    * @Params idAccount The UUID of the user
    * @Return User The found user entity
    * @Throw IdNotFoundException if user not found
    */
    @Override
    public User readyUser(UUID idAccount) throws IdNotFoundException {
        return service.getUser(idAccount);
    }

    /*
    * Retrieves all users
    * @Return List of all users
    */
    @Override
    public List<User> UsersList() {
        return service.getUsers();
    }

    /*
    * Deletes a user by ID
    * @Params idAccount The UUID of the user to delete
    * @Throw IdNotFoundException if user not found
    */
    @Override
    public void readyToDeleteUser(UUID idAccount) throws IdNotFoundException {
        service.deleteUser(idAccount);
    }

    /*
    * Creates a new user with an account type
    * @Params user The user entity to create
    * @Params typeOfAccount The account type name
    * @Return User The created user entity
    * @Throw DuplicateElementException if email or username already exists
    */
    @Override
    public User readyToCreateUser(User user, String typeOfAccount) throws DuplicateElementException {
        return service.createUser(user, typeOfAccount);
    }

    /*
    * Updates an existing user
    * @Params idAccount The UUID of the user to update
    * @Params user The user entity with updated data
    * @Return User The updated user entity
    * @Throw IdNotFoundException if user not found
    */
    @Override
    public User readyUpdateUser(UUID idAccount, User user) throws IdNotFoundException {
        return service.updateUser(idAccount, user);
    }
    /*
    * Confirmations of user existence with relation to guardians
    * @Params id The UUID of the user
    * @Return Boolean True if user exists, false otherwise
    * */
    @Override
    public Boolean userExists(UUID id){
        return service.userExists(id);
    }

    /*
    * Retrieves user info for authentication by username
    * @Params username The username to look up
    * @Return User The found user entity
    * @Throw UsernameAuthException if username not found
    */
    @Override
    public User readyUserInfoAuth(String username) throws UsernameAuthException{
        return service.infoUserAuth(username);
    }
}
