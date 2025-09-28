package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.GuardianPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class GuardianController implements GuardianContract.View {
    private final GuardianPresenter guardianPresenter;

    public GuardianController(GuardianPresenter guardianPresenter) {
        this.guardianPresenter = guardianPresenter;
    }

    @GetMapping("/Guardian/guardianData/{idSearch}")
    @Override
    public ResponseEntity<ApiResponseOK<GuardianDTO>> showGuardian(
            @PathVariable("idSearch") UUID idSearch,
            @RequestBody User user) {
        GuardianDTO guardianDTO = guardianPresenter.readyGuardian(idSearch, user);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "guardian data",
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
}
