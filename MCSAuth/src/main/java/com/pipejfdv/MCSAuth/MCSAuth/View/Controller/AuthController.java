package com.pipejfdv.MCSAuth.MCSAuth.View.Controller;

import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthResponse;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Presenter.Interfaces.UserContractFM;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.OK.UserFMDataOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController implements UserContractFM.View {
    private final UserContractFM.Presenter presenter;

    public AuthController(UserContractFM.Presenter presenter) {
        this.presenter = presenter;
    }
    /*
    * This showLogin method is responsible for confirming the login and assigning a token to the user
    * @Params UUID idUser
    * @Return ResponseEntity with OK 200
    */
    @PostMapping("/login")
    @Override
    public ResponseEntity<UserFMDataOK<AuthResponse>> showLogin(@PathVariable UUID idUser) {
        AuthResponse response = presenter.authenticate(idUser);
        return ResponseEntity.ok(new UserFMDataOK<>(
                "Login exitoso",
                response,
                HttpStatus.OK.value()
        ));
    }
    /*
    *  This showRegistry method is used to generate an assignment token when creating the user.
    *  @Param json User
    *  @Param String typeAccount(Demo User, Premium User, Admin, Medic)
    *  @Return ResponseEntity with OK 200
    */
}