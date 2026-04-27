package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Profile;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ProfilesAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ProfilesPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.ProfilesServices;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.UserService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ProfilesContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProfilesPresenter implements ProfilesContract.Presenter{
    private final ProfilesServices profilesServices;
    private final UserService userService;

    public ProfilesPresenter(ProfilesServices profilesServices, UserService userService) {
        this.profilesServices = profilesServices;
        this.userService = userService;
    }

    @Override
    public ProfilesAdminDTO createProfiles(String profiles, UUID childrenId) throws DuplicateElementException {
        Profile profile = profilesServices.createProfiles(profiles, childrenId);
        return new ProfilesAdminDTO(
                profile.getId(),
                profile.getNameProfile(),
                profile.getLastAccess()
        );
    }

    @Override
    public ProfilesPublicDTO getProfilesPublic(UUID id) throws IdNotFoundException {
        Profile profile = profilesServices.getProfiles(id);
        return new ProfilesPublicDTO(
                profile.getId(),
                profile.getNameProfile()
        );
    }

    @Override
    public ProfilesAdminDTO getProfilesAdmin(UUID id) throws IdNotFoundException {
        Profile profile = profilesServices.getProfiles(id);
        return new ProfilesAdminDTO(
                profile.getId(),
                profile.getNameProfile(),
                profile.getLastAccess()
        );
    }

    @Override
    public List<ProfilesAdminDTO> listProfilesAdmin() {
        return profilesServices.listProfilesAdmin().stream()
                .map(profiles -> new ProfilesAdminDTO(
                        profiles.getId(),
                        profiles.getNameProfile(),
                        profiles.getLastAccess()
                )).toList();
    }

    @Override
    public List<ProfilesPublicDTO> listProfilesPublic(UUID userID) {
        UUID guardianId = userService.getUser(userID).getGuardian().getId();
        return profilesServices.listProfilesPublic(guardianId).stream()
                .map(profiles -> new ProfilesPublicDTO(
                        profiles.getId(),
                        profiles.getNameProfile()
                )).toList();
    }

    @Override
    public ProfilesPublicDTO updateProfiles(UUID id, String name) throws IdNotFoundException {
        Profile profile = profilesServices.updateProfiles(id, name);
        return new ProfilesPublicDTO(
                profile.getId(),
                profile.getNameProfile()
        );
    }
}
