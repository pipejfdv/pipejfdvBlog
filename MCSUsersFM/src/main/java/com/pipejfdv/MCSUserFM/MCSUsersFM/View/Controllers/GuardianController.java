package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianAdminDTO;
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
        // Usamos readyGuardianAdmin para obtener todos los datos antes de eliminar
        GuardianAdminDTO guardianAdminDTO = guardianPresenter.readyGuardianAdmin(id);
        // Convertimos a GuardianDTO para mantener compatibilidad con la respuesta esperada
        GuardianDTO guardianDTO = new GuardianDTO(
                guardianAdminDTO.getId(),
                guardianAdminDTO.getName(),
                guardianAdminDTO.getLastname(),
                guardianAdminDTO.getPhone(),
                guardianAdminDTO.getBiography(),
                guardianAdminDTO.getUsername(),
                guardianAdminDTO.getEmail(),
                guardianAdminDTO.getAccountType(),
                guardianAdminDTO.getDocument(),
                guardianAdminDTO.getDocumentType()
        );
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian & user delete",
                guardianDTO,
                HttpStatus.OK.value()
        ));
    }
    @PostMapping("/Guardian/create/{idUserAssignment}/{typeDocument}")
    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showCreateGuardian(
            @RequestBody Guardian guardian,
            @PathVariable UUID idUserAssignment,
            @PathVariable UUID typeDocument) {
        Guardian newGuardian = guardianPresenter.readyToCreateUser(guardian, idUserAssignment, typeDocument);
        GuardianDTO guardianDTO = new GuardianDTO(
                newGuardian.getId(),
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
