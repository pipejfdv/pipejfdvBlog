package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Profile;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.ProfilesRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ProfilesContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service for managing Profile entities
* Handles CRUD operations and public/admin profile listing
*/
@Service
public class ProfilesServices implements ProfilesContract.Model{
    private final ProfilesRepository profilesRepository;
    private final ChildrenService childrenService;

    public ProfilesServices(ProfilesRepository profilesRepository, ChildrenService childrenService) {
        this.profilesRepository = profilesRepository;
        this.childrenService = childrenService;
    }

    /*
    * Creates a new profile for a children
    * @Params profiles The profile name
    * @Params childrenId The UUID of the associated children
    * @Return Profile The saved profile entity
    * @Throw DuplicateElementException if profile name already exists
    */
    @Override
    public Profile createProfiles(String profiles, UUID childrenId) throws DuplicateElementException {
        if (profilesRepository.existsByNameProfile(profiles)) {
            throw new DuplicateElementException(profiles);
        }
        return profilesRepository.save(new Profile(profiles, childrenService.getChildren(childrenId)));
    }

    /*
    * Retrieves a profile by its ID
    * @Params id The UUID of the profile
    * @Return Profile The found profile entity
    * @Throw IdNotFoundException if profile not found
    */
    @Override
    public Profile getProfiles(UUID id) throws IdNotFoundException {
        return profilesRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    /*
    * Retrieves all profiles for admin view
    * @Return List of all profiles
    */
    @Override
    public List<Profile> listProfilesAdmin() {
        return profilesRepository.findAll();
    }

    /*
    * Retrieves profiles visible to a specific guardian
    * @Params guardianId The UUID of the guardian
    * @Return List of public profiles for the guardian
    */
    @Override
    public List<Profile> listProfilesPublic(UUID guardianId) {
        return profilesRepository.listProfilesPublic(guardianId);
    }

    /*
    * Updates the name of a profile
    * @Params id The UUID of the profile to update
    * @Params name The new profile name
    * @Return Profile The updated profile entity
    * @Throw IdNotFoundException if profile not found
    */
    @Override
    public Profile updateProfiles(UUID id, String name) throws IdNotFoundException {
        return profilesRepository.findById(id).map(profiles -> {
            profiles.setNameProfile(name);
            return profilesRepository.save(profiles);
        }).orElseThrow(() -> new IdNotFoundException(id));
    }
}
