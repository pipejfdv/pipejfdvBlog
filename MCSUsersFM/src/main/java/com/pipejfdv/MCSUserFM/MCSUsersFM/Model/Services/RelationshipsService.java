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

/*
* Service for managing Relationship entities
* Handles CRUD operations and lookup by ID or name
*/
@Service
public class RelationshipsService implements RelationshipsContract.Model {
    private final RelationshipsRepository relationshipsRepository;

    public RelationshipsService(RelationshipsRepository relationshipsRepository) {
        this.relationshipsRepository = relationshipsRepository;
    }
    /*CRUD*/
    /*Cread*/
    /*
    * Creates a new relationship type
    * @Params relationships The name of the relationship
    * @Return Relationships The saved relationship entity
    * @Throw DuplicateElementException if relationship already exists
    */
    @Override
    public Relationships createRelationships(String relationships) throws DuplicateElementException {
        if(relationshipsRepository.existsByRelationship(relationships) ){
            throw new DuplicateElementException(relationships);
        }
        Relationships relation = new Relationships(relationships);
        return relationshipsRepository.save(relation);
    }

    /*
    * Retrieves a relationship by ID or name
    * @Params id The UUID of the relationship (optional)
    * @Params relation The name of the relationship (optional)
    * @Return Relationships The found relationship entity
    * @Throw IdNotFoundException if searched by ID and not found
    * @Throw NameNotFoundException if searched by name and not found
    */
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

    /*
    * Retrieves all relationship types
    * @Return List of all relationships
    */
    @Override
    public List<Relationships> listRelationships() {
        return relationshipsRepository.findAll();
    }

    /*
    * Updates the name of a relationship
    * @Params id The UUID of the relationship to update
    * @Params relation The new relationship name
    * @Return Relationships The updated relationship entity
    * @Throw IdNotFoundException if relationship not found
    */
    @Override
    public Relationships updateRelationships(UUID id, String relation) throws IdNotFoundException {
        Relationships oldRelation = relationshipsRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        oldRelation.setRelationship(relation);
        return relationshipsRepository.save(oldRelation);
    }

}
