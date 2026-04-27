package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Profile;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ProfilesAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ProfilesPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ProfilesContract {
    interface View {
        ResponseEntity<ApiResponseOK<ProfilesAdminDTO>> createProfiles(String profiles, UUID childrenId) throws DuplicateElementException;
        ResponseEntity<ApiResponseOK<ProfilesPublicDTO>> getProfilesPublic(UUID id) throws IdNotFoundException;
        ResponseEntity<ApiResponseOK<ProfilesAdminDTO>> getProfilesAdmin(UUID id) throws IdNotFoundException;
        ResponseEntity<ApiResponseOK<List<ProfilesAdminDTO>>> listProfilesAdmin();
        ResponseEntity<ApiResponseOK<List<ProfilesPublicDTO>>> listProfilesPublic(UUID guardianId);
        ResponseEntity<ApiResponseOK<ProfilesPublicDTO>> updateProfiles(UUID id, String name) throws IdNotFoundException;
    }
    interface Presenter {
        ProfilesAdminDTO createProfiles(String profiles, UUID childrenId) throws DuplicateElementException;
        ProfilesPublicDTO getProfilesPublic(UUID id) throws IdNotFoundException;
        ProfilesAdminDTO getProfilesAdmin(UUID id) throws IdNotFoundException;
        List<ProfilesAdminDTO> listProfilesAdmin();
        List<ProfilesPublicDTO> listProfilesPublic(UUID guardianId);
        ProfilesPublicDTO updateProfiles(UUID id, String name) throws IdNotFoundException;
    }
    interface Model {
        Profile createProfiles(String profiles, UUID childrenId) throws DuplicateElementException;
        Profile getProfiles(UUID id) throws IdNotFoundException;
        List<Profile> listProfilesAdmin();
        List<Profile> listProfilesPublic(UUID guardianId);
        Profile updateProfiles(UUID id, String name) throws IdNotFoundException;
    }
}
