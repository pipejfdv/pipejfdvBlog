package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ProfilesAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ProfilesPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.ProfilesPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ProfilesContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class ProfilesController implements ProfilesContract.View {
    private final ProfilesPresenter profilesPresenter;

    public ProfilesController(ProfilesPresenter profilesPresenter) {
        this.profilesPresenter = profilesPresenter;
    }

    @Override
    @PostMapping("/profiles/create/{childrenId}/{profiles}")
    public ResponseEntity<ApiResponseOK<ProfilesAdminDTO>> createProfiles(
            @PathVariable String profiles,
            @PathVariable UUID childrenId) throws DuplicateElementException {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile created successfully",
                profilesPresenter.createProfiles(profiles, childrenId),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/profiles/get/{id}")
    public ResponseEntity<ApiResponseOK<ProfilesPublicDTO>> getProfilesPublic(
            @PathVariable UUID id) throws IdNotFoundException {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile retrieved successfully",
                profilesPresenter.getProfilesPublic(id),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/profiles/getAdmin/{id}")
    public ResponseEntity<ApiResponseOK<ProfilesAdminDTO>> getProfilesAdmin(
            @PathVariable UUID id) throws IdNotFoundException {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile retrieved successfully",
                profilesPresenter.getProfilesAdmin(id),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/profiles/list")
    public ResponseEntity<ApiResponseOK<List<ProfilesAdminDTO>>> listProfilesAdmin() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile retrieved successfully",
                profilesPresenter.listProfilesAdmin(),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/profiles/public/list/{guardianId}")
    public ResponseEntity<ApiResponseOK<List<ProfilesPublicDTO>>> listProfilesPublic(
            @PathVariable UUID guardianId) {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile retrieved successfully",
                profilesPresenter.listProfilesPublic(guardianId),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @PutMapping("/profiles/update/{id}/{name}")
    public ResponseEntity<ApiResponseOK<ProfilesPublicDTO>> updateProfiles(
            @PathVariable UUID id,
            @PathVariable String name) throws IdNotFoundException {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Profile updated successfully",
                profilesPresenter.updateProfiles(id, name),
                HttpStatus.OK.value()
        ));
    }
}
