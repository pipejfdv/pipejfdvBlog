package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

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
    public GuardianController(GuardianPresenter guardianPresenter, UserPresenter userPresenter) {
        this.guardianPresenter = guardianPresenter;
        this.userPresenter = userPresenter;
    }

    // Endpoint para User y Therapist - retorna GuardianPublicDTO
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

    // Endpoint para Admin - retorna GuardianAdminDTO
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

    
    @GetMapping("/Guardian/list")
    @Override
    public ResponseEntity<ApiResponseOK<List<GuardianPublicDTO>>> showGuardians() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian list",
                guardianPresenter.readyGuardians(),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @DeleteMapping("/Guardian/delete/{id}")
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> showDeleteGuardian(@PathVariable UUID id) {
        guardianPresenter.readyToDeleteGuardian(id);
        // Usamos readyGuardianAdmin para obtener todos los datos antes de eliminar
        GuardianAdminDTO guardianAdminDTO = guardianPresenter.readyGuardianAdmin(id);
        // Convertimos a GuardianPublicDTO para mantener compatibilidad con la respuesta esperada
        GuardianPublicDTO guardianDTO = new GuardianPublicDTO(
                guardianAdminDTO.getId(),
                guardianAdminDTO.getName(),
                guardianAdminDTO.getLastname(),
                guardianAdminDTO.getPhone(),
                guardianAdminDTO.getBiography(),
                guardianAdminDTO.getEmail()
        );
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian & user delete",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }
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

    @Override
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> showEditGuardian(UUID id, Guardian guardian) {
        return null;
    }

    /*
     *  search for guardian using user ID
     */
    @Override
    public ResponseEntity<ApiResponseOK<GuardianPublicDTO>> showToSearchGuardianForUserId(User userId) {
        return null;
    }
}
