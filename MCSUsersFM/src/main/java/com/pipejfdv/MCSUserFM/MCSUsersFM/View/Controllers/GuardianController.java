package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.GuardianPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.UserPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class GuardianController implements GuardianContract.View {
    private final GuardianPresenter guardianPresenter;
    private final UserPresenter userPresenter;
    public GuardianController(GuardianPresenter guardianPresenter, UserPresenter userPresenter) {
        this.guardianPresenter = guardianPresenter;
        this.userPresenter = userPresenter;
    }

    @GetMapping("/Guardian/guardianData")
    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showGuardian(
            @RequestParam(value = "id", required = false) String idSearch,
            Authentication authentication) {
        JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
        String rol = jwtAuth.getToken().getClaimAsString("accountType");
        if(idSearch == null || idSearch.isBlank()){
            User user =  userPresenter.readyUser(UUID.fromString(jwtAuth.getToken().getId()));
            UUID idGuardian = guardianPresenter.readyToSearchGuardianForUserId(user).getId();
            GuardianDTO guardianDTO = guardianPresenter.readyGuardian(idGuardian, rol);
            return ResponseEntity.ok(new ApiResponseOK<>(
                    "guardian data",
                    guardianDTO,
                    HttpStatus.OK.value()
            ));
        }else {
            GuardianDTO guardianDTO = guardianPresenter.readyGuardian(UUID.fromString(idSearch), rol);
            return ResponseEntity.ok(new ApiResponseOK<>(
                    "guardian data",
                    guardianDTO,
                    HttpStatus.OK.value()
            ));
        }
    }

    @GetMapping("/Guardian/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<GuardianDTO>>> showGuardians() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian list",
                guardianPresenter.readyGuardians(),
                HttpStatus.OK.value()
        ));
    }

    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showDeleteGuardian(UUID id) {
        guardianPresenter.readyToDeleteGuardian(id);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian & user delete",
                guardianPresenter.readyGuardian(id, null),
                HttpStatus.OK.value()
        ));
    }
    @PostMapping("/Guardian/create/{idUserAssignment}/{typeDocument}")
    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showCreateGuardian(
            @RequestBody Guardian guardian,
            @PathVariable("idUserAssignment") UUID idUserAssignment,
            @PathVariable("typeDocument") UUID typeDocument) {
        Guardian newGuardian = guardianPresenter.readyToCreateUser(guardian, idUserAssignment, typeDocument);
        GuardianDTO guardianDTO = new GuardianDTO(
                newGuardian.getName(),
                newGuardian.getLastname(),
                newGuardian.getPhone(),
                null,
                newGuardian.getUser().getUsername(),
                newGuardian.getUser().getEmail(),
                null,
                null,
                null);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian created",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }

    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showEditGuardian(UUID id, Guardian guardian) {
        return null;
    }

    /*
     *  search for guardian using user ID
     */
    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showToSearchGuardianForUserId(User userId) {
        return null;
    }
}
