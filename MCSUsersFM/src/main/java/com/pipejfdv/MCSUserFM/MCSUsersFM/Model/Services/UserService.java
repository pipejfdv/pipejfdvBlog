package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.UserRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserContract.Model {
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    /*CONSTRUCTOR*/
    public UserService(UserRepository userRepository, AccountTypeRepository accountTypeRepository) {
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
    }
    /*CRUD*/
    @Override
    public User getUser(UUID id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                        .orElseThrow(()-> new IdNotFoundException(id));
        userRepository.delete(user);
    }

    @Override
    public User createUser(User user, String typeOfAccount) throws DuplicateElementException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateElementException(user.getEmail());
        }
        AccountType ac = accountTypeRepository.findAccountTypeByName(typeOfAccount);
        user.setAccountType(ac);
        user.setId(UUID.randomUUID());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID id, User user) throws IdNotFoundException {
        User oldUser = userRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
        oldUser.setId(oldUser.getId());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setUsername(user.getUsername());
        userRepository.save(oldUser);
        return oldUser;
    }
}
