package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.RelationshipsDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.RelationshipsService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.RelationshipsContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
/*
* Presenter for Relationship operations
* Acts as intermediary between controller and RelationshipsService
* Maps entities to DTOs for public and admin views
*/
@Component
public class RelationshipsPresenter implements RelationshipsContract.Presenter{
    private final RelationshipsService relationshipsService;

    public RelationshipsPresenter(RelationshipsService relationshipsService) {
        this.relationshipsService = relationshipsService;
    }

    /*
    * Creates a new relationship type
    * @Params relationships The name of the relationship
    * @Return Relationships The created relationship entity
    * @Throw DuplicateElementException if relationship already exists
    */
    @Override
    public Relationships createRelationships(String relationships) throws DuplicateElementException {
        return relationshipsService.createRelationships(relationships);
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
        return relationshipsService.getRelationships(id, relation);
    }

    /*
    * Retrieves all relationships as public DTOs
    * @Return List of relationship DTOs
    */
    @Override
    public List<RelationshipsDTO> listRelationshipsPublic() {
        return relationshipsService.listRelationships().stream()
                .map(r -> new RelationshipsDTO(
                        r.getId(),
                        r.getRelationship()
                )).toList();
    }

    /*
    * Retrieves all relationships as admin entities
    * @Return List of all relationship entities
    */
    @Override
    public List<Relationships> listRelationshipsAdmin() {
        return relationshipsService.listRelationships();
    }

    /*
    * Updates a relationship name
    * @Params id The UUID of the relationship to update
    * @Params relation The new relationship name
    * @Return Relationships The updated relationship entity
    * @Throw IdNotFoundException if relationship not found
    */
    @Override
    public Relationships updateRelationships(UUID id, String relation) throws IdNotFoundException {
        return relationshipsService.updateRelationships(id, relation);
    }

}
