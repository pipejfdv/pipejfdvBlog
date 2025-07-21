package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.UserService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserPresenter implements UserContract.Presenter {
    private final UserService service;

    public UserPresenter(UserService service) {
        this.service = service;
    }

    @Override
    public User readyUser(UUID idAccount) throws IdNotFoundException {
        return service.getUser(idAccount);
    }

    @Override
    public List<User> UsersList() {
        return service.getUsers();
    }

    @Override
    public void readyToDeleteUser(UUID idAccount) throws IdNotFoundException {
        service.deleteUser(idAccount);
    }

    @Override
    public User readyToCreateUser(User user) throws DuplicateElementException {
        return service.createUser(user);
    }

    @Override
    public User readyUpdateUser(UUID idAccount, User user) throws IdNotFoundException {
        return service.updateUser(idAccount, user);
    }
}
