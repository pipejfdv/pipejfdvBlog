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

/*
* Service for managing User entities
* Handles CRUD operations and authentication info
*/
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
    /*
    * Retrieves a user by their ID
    * @Params id The UUID of the user
    * @Return User The found user entity
    * @Throw IdNotFoundException if user not found
    */
    @Override
    public User getUser(UUID id) throws IdNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()-> new IdNotFoundException(id));
    }

    /*
    * Retrieves all users from the database
    * @Return List of all users
    */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /*
    * Deletes a user by their ID
    * @Params id The UUID of the user to delete
    * @Throw IdNotFoundException if user not found
    */
    @Override
    public void deleteUser(UUID id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                        .orElseThrow(()-> new IdNotFoundException(id));
        userRepository.delete(user);
    }

    /*
    * Creates a new user with a specific account type
    * @Params user The user entity to create
    * @Params typeOfAccount The name of the account type
    * @Return User The saved user entity
    * @Throw DuplicateElementException if email or username already exists
    * @Throw NameNotFoundException if account type not found
    */
    @Override
    public User createUser(User user, String typeOfAccount) throws DuplicateElementException, NameNotFoundException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateElementException(user.getEmail());
        }
        if(userRepository.existsByUsername(user.getUsername())){
            throw new DuplicateElementException(user.getUsername());
        }
        AccountType ac = accountTypeRepository.findAccountTypeByName(typeOfAccount)
                .orElseThrow(()-> new NameNotFoundException(typeOfAccount));
        user.setAccountType(ac);
        user.setId(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /*
    * Updates an existing user's email, password and username
    * @Params id The UUID of the user to update
    * @Params user The user entity with updated data
    * @Return User The updated user entity
    * @Throw IdNotFoundException if user not found
    */
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
    * Retrieves user info for MCSAuth by username
    * @Params username The username to look up
    * @Return User The user entity
    * @Throw UsernameAuthException if username not found
    */
    @Override
    public User infoUserAuth(String username) throws UsernameAuthException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameAuthException(username));
        return user;
    }
}
