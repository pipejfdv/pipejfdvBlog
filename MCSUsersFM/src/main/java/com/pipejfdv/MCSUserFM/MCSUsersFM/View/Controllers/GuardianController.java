package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Config.JwtUtils;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.GuardianPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.GuardianAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.GuardianPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.UserPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class GuardianController implements GuardianContract.View {
    private final GuardianPresenter guardianPresenter;
    private final UserPresenter userPresenter;
    private final JwtUtils jwtUtils;
    public GuardianController(GuardianPresenter guardianPresenter,
                              UserPresenter userPresenter,
                              JwtUtils jwtUtils) {
        this.guardianPresenter = guardianPresenter;
        this.userPresenter = userPresenter;
        this.jwtUtils = jwtUtils;
    }

    /*
    * Gets guardian public data for User and Therapist roles
    * @Params idSearch UUID of the guardian (optional)
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with guardian public data
    */
    @GetMapping("/Guardian/public")
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> showGuardianPublic(
            @RequestParam(value = "id", required = false) String idSearch,
            Authentication authentication) {
        
        UUID guardianId;
        if(idSearch == null || idSearch.isBlank()){
            User user = userPresenter.readyUser(UUID.fromString(((JwtAuthenticationToken) authentication).getToken().getId()));
            guardianId = guardianPresenter.readyToSearchGuardianForUserId(user).getId();
        } else {
            guardianId = UUID.fromString(idSearch);
        }
        
        GuardianPublicDTO guardianDTO = guardianPresenter.readyGuardianPublic(guardianId);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian public data",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Gets guardian admin data for Admin role
    * @Params idSearch UUID of the guardian (optional)
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with guardian admin data
    */
    @GetMapping("/Guardian/admin")
    public ResponseEntity<ApiResponseOK<GuardianAdminDTO>> showGuardianAdmin(
            @RequestParam(value = "id", required = false) String idSearch,
            Authentication authentication) {
        
        UUID guardianId;
        if(idSearch == null || idSearch.isBlank()){
            User user = userPresenter.readyUser(UUID.fromString(((JwtAuthenticationToken) authentication).getToken().getId()));
            guardianId = guardianPresenter.readyToSearchGuardianForUserId(user).getId();
        } else {
            guardianId = UUID.fromString(idSearch);
        }
        
        GuardianAdminDTO guardianDTO = guardianPresenter.readyGuardianAdmin(guardianId);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian admin data",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }

    
    /*
    * Returns a list of all guardians as public DTOs
    * @Return ResponseEntity with list of guardian public data
    */
    @GetMapping("/Guardian/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<GuardianPublicDTO>>> showGuardians() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian list",
                guardianPresenter.readyGuardians(),
                HttpStatus.OK.value()
        ));
    }

    /*
    * Deletes a guardian and its associated user
    * @Params id UUID of the guardian (optional)
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with boolean deletion result
    */
    @Override
    @DeleteMapping("/Guardian/delete")
    public ResponseEntity<ApiResponseOK<Boolean>> deleteGuardian(
            @RequestParam(value = "id", required = false) String id,
            Authentication authentication) {
        UUID targetId;
        if (id == null || id.isBlank()){
            User user = userPresenter.readyUser(UUID.fromString(((JwtAuthenticationToken) authentication).getToken().getId()));
            targetId = guardianPresenter.readyToSearchGuardianForUserId(user).getId();
        } else {
            targetId = UUID.fromString(id);
        }
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian & user delete",
                guardianPresenter.readyToDeleteGuardian(targetId),
                HttpStatus.OK.value()
        ));
    }
    /*
    * Creates a new guardian with user assignment and document type
    * @Params guardian Guardian object with personal data
    * @Params idUserAssignment UUID of the user to assign
    * @Params typeDocument UUID of the document type
    * @Return ResponseEntity with created guardian public data
    */
    @PostMapping("/Guardian/create/{idUserAssignment}/{typeDocument}")
    @Override
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> createGuardian(
            @RequestBody Guardian guardian,
            @PathVariable UUID idUserAssignment,
            @PathVariable UUID typeDocument) {
        Guardian newGuardian = guardianPresenter.readyToCreateUser(guardian, idUserAssignment, typeDocument);
        GuardianPublicDTO guardianDTO = new GuardianPublicDTO(
                newGuardian.getId(),
                newGuardian.getName(),
                newGuardian.getLastname(),
                newGuardian.getPhone(),
                newGuardian.getBiography(),
                newGuardian.getUser().getEmail()
                );
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian created",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Updates an existing guardian's information
    * @Params id UUID of the guardian (optional)
    * @Params guardian Guardian object with updated data
    * @Params authentication Spring Security authentication object
    * @Return ResponseEntity with updated guardian public data
    */
    @Override
    @PutMapping("/Guardian/edit")
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> editGuardian(
            @RequestParam(name = "id", required = false) String id,
            @RequestBody Guardian guardian,
            Authentication authentication) {
        UUID targetId;
        if (id == null || id.isBlank()){
            User user = userPresenter.readyUser(UUID.fromString(((JwtAuthenticationToken) authentication).getToken().getId()));
            targetId = guardianPresenter.readyToSearchGuardianForUserId(user).getId();
        } else {
            targetId = UUID.fromString(id);
        }
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian edited",
                guardianPresenter.readyToUpdateUser(targetId, guardian),
                HttpStatus.OK.value()
        ));
    }
}
