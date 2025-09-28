package com.pipejfdv.MCSAuth.MCSAuth.View.Controller;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.UserCredentials;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController implements UserContractFM.View {
    private final UserContractFM.Presenter presenter;
    private MCSUsersFMServices mcsUsersFMServices;

    public AuthController(UserContractFM.Presenter presenter, MCSUsersFMServices mcsUsersFMServices) {
        this.presenter = presenter;
        this.mcsUsersFMServices = mcsUsersFMServices;
    }
    /*
    * This Login method is responsible for confirming the login and assigning a token to the user
    * @Params UserCredentials(Body/JSON) user
    * @Return ResponseEntity with OK 200
    */
    @PostMapping("/login")
    @Override
    public ResponseEntity<UserFMDataOK<AuthResponse>> Login(@RequestBody UserCredentials user) {
        AuthResponse response = presenter.authenticate(user);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Login exitoso",
                response,
                HttpStatus.OK.value()
        ));
    }

    @GetMapping("/prueba/{username}")
    public UserPassDTO prueba(@PathVariable String username){
        UserPassDTO user = mcsUsersFMServices.getCredentialsUser(username);
        log.info("prueba user: {}", user.getUsername());

        return user;
    }

}