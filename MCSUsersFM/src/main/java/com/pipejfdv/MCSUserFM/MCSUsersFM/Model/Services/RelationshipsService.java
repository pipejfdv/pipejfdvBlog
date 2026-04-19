package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.RelationshipsRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.RelationshipsContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RelationshipsService implements RelationshipsContract.Model {
    private final RelationshipsRepository relationshipsRepository;

    public RelationshipsService(RelationshipsRepository relationshipsRepository) {
        this.relationshipsRepository = relationshipsRepository;
    }
    /*CRUD*/
    /*Cread*/
    @Override
    public Relationships createRelationships(String relationships) throws DuplicateElementException {
        if(relationshipsRepository.existsByRelationship(relationships) ){
            throw new DuplicateElementException(relationships);
        }
        Relationships relation = new Relationships(relationships);
        return relationshipsRepository.save(relation);
    }

    @Override
    public Relationships getRelationships(UUID id, String relation) throws IdNotFoundException, NameNotFoundException {
        if (id != null) {
            return relationshipsRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        }
        Relationships r = relationshipsRepository.findByRelationship(relation);
        if (r == null) {
            throw new NameNotFoundException(relation);
        }
        return r;
    }

    @Override
    public List<Relationships> listRelationships() {
        return relationshipsRepository.findAll();
    }

    @Override
    public Relationships updateRelationships(UUID id, String relation) throws IdNotFoundException {
        Relationships oldRelation = relationshipsRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        oldRelation.setRelationship(relation);
        return relationshipsRepository.save(oldRelation);
    }

}
