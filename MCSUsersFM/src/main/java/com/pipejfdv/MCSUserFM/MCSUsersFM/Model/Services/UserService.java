package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.UsernameAuthException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.UserRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.UserContract;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserContract.Model {
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final PasswordEncoder passwordEncoder;
    /*CONSTRUCTOR*/
    public UserService(UserRepository userRepository,
                       AccountTypeRepository accountTypeRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /*CRUD*/
    @Override
    public User getUser(UUID id) throws IdNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
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
    public User createUser(User user, String typeOfAccount) throws DuplicateElementException, NameNotFoundException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateElementException(user.getEmail());
        }
        AccountType ac = accountTypeRepository.findAccountTypeByName(typeOfAccount)
                .orElseThrow(()-> new NameNotFoundException(typeOfAccount));
        user.setAccountType(ac);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    /*
    data intended for MCSAuth user
     */
    @Override
    public User infoUserAuth(String username) throws UsernameAuthException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameAuthException(username));
        return user;
    }
}
