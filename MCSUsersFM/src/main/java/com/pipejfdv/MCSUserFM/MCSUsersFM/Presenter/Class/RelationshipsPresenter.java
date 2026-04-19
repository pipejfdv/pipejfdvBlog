package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.RelationshipsService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.RelationshipsContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class RelationshipsPresenter implements RelationshipsContract.Presenter{
    private final RelationshipsService relationshipsService;

    public RelationshipsPresenter(RelationshipsService relationshipsService) {
        this.relationshipsService = relationshipsService;
    }

    @Override
    public Relationships createRelationships(String relationships) throws DuplicateElementException {
        return relationshipsService.createRelationships(relationships);
    }

    @Override
    public Relationships getRelationships(UUID id, String relation) throws IdNotFoundException, NameNotFoundException {
        return relationshipsService.getRelationships(id, relation);
    }

    @Override
    public List<Relationships> listRelationships() {
        return relationshipsService.listRelationships();
    }

    @Override
    public Relationships updateRelationships(UUID id, String relation) throws IdNotFoundException {
        return relationshipsService.updateRelationships(id, relation);
    }

}
