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

/*
* Presenter for Profile operations
* Acts as intermediary between controller and ProfilesServices
* Maps entities to DTOs for public and admin views
*/
@Component
public class ProfilesPresenter implements ProfilesContract.Presenter{
    private final ProfilesServices profilesServices;
    private final UserService userService;

    public ProfilesPresenter(ProfilesServices profilesServices, UserService userService) {
        this.profilesServices = profilesServices;
        this.userService = userService;
    }

    /*
    * Creates a new profile for a children and returns an admin DTO
    * @Params profiles The profile name
    * @Params childrenId The UUID of the associated children
    * @Return ProfilesAdminDTO The admin DTO of the created profile
    * @Throw DuplicateElementException if profile name already exists
    */
    @Override
    public ProfilesAdminDTO createProfiles(String profiles, UUID childrenId) throws DuplicateElementException {
        Profile profile = profilesServices.createProfiles(profiles, childrenId);
        return new ProfilesAdminDTO(
                profile.getId(),
                profile.getNameProfile(),
                profile.getLastAccess()
        );
    }

    /*
    * Retrieves a profile by ID and returns a public DTO
    * @Params id The UUID of the profile
    * @Return ProfilesPublicDTO The public DTO of the profile
    * @Throw IdNotFoundException if profile not found
    */
    @Override
    public ProfilesPublicDTO getProfilesPublic(UUID id) throws IdNotFoundException {
        Profile profile = profilesServices.getProfiles(id);
        return new ProfilesPublicDTO(
                profile.getId(),
                profile.getNameProfile()
        );
    }

    /*
    * Retrieves a profile by ID and returns an admin DTO with last access info
    * @Params id The UUID of the profile
    * @Return ProfilesAdminDTO The admin DTO of the profile
    * @Throw IdNotFoundException if profile not found
    */
    @Override
    public ProfilesAdminDTO getProfilesAdmin(UUID id) throws IdNotFoundException {
        Profile profile = profilesServices.getProfiles(id);
        return new ProfilesAdminDTO(
                profile.getId(),
                profile.getNameProfile(),
                profile.getLastAccess()
        );
    }

    /*
    * Retrieves all profiles as admin DTOs
    * @Return List of admin DTOs for all profiles
    */
    @Override
    public List<ProfilesAdminDTO> listProfilesAdmin() {
        return profilesServices.listProfilesAdmin().stream()
                .map(profiles -> new ProfilesAdminDTO(
                        profiles.getId(),
                        profiles.getNameProfile(),
                        profiles.getLastAccess()
                )).toList();
    }

    /*
    * Retrieves public profiles for a specific user's guardian
    * @Params userID The UUID of the user
    * @Return List of public profile DTOs
    */
    @Override
    public List<ProfilesPublicDTO> listProfilesPublic(UUID userID) {
        UUID guardianId = userService.getUser(userID).getGuardian().getId();
        return profilesServices.listProfilesPublic(guardianId).stream()
                .map(profiles -> new ProfilesPublicDTO(
                        profiles.getId(),
                        profiles.getNameProfile()
                )).toList();
    }

    /*
    * Updates a profile name and returns a public DTO
    * @Params id The UUID of the profile to update
    * @Params name The new profile name
    * @Return ProfilesPublicDTO The public DTO of the updated profile
    * @Throw IdNotFoundException if profile not found
    */
    @Override
    public ProfilesPublicDTO updateProfiles(UUID id, String name) throws IdNotFoundException {
        Profile profile = profilesServices.updateProfiles(id, name);
        return new ProfilesPublicDTO(
                profile.getId(),
                profile.getNameProfile()
        );
    }
}
