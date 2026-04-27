package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Profile;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.ProfilesRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ProfilesContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfilesServices implements ProfilesContract.Model{
    private final ProfilesRepository profilesRepository;
    private final ChildrenService childrenService;

    public ProfilesServices(ProfilesRepository profilesRepository, ChildrenService childrenService) {
        this.profilesRepository = profilesRepository;
        this.childrenService = childrenService;
    }

    @Override
    public Profile createProfiles(String profiles, UUID childrenId) throws DuplicateElementException {
        if (profilesRepository.existsByNameProfile(profiles)) {
            throw new DuplicateElementException(profiles);
        }
        return profilesRepository.save(new Profile(profiles, childrenService.getChildren(childrenId)));
    }

    @Override
    public Profile getProfiles(UUID id) throws IdNotFoundException {
        return profilesRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    public List<Profile> listProfilesAdmin() {
        return profilesRepository.findAll();
    }

    @Override
    public List<Profile> listProfilesPublic(UUID guardianId) {
        return profilesRepository.listProfilesPublic(guardianId);
    }

    @Override
    public Profile updateProfiles(UUID id, String name) throws IdNotFoundException {
        return profilesRepository.findById(id).map(profiles -> {
            profiles.setNameProfile(name);
            return profilesRepository.save(profiles);
        }).orElseThrow(() -> new IdNotFoundException(id));
    }
}
