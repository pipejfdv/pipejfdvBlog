package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.RelationshipsDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.RelationshipsPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.RelationshipsContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class RelationshipsController implements RelationshipsContract.View {
    final private RelationshipsPresenter relationshipsPresenter;
    public RelationshipsController(RelationshipsPresenter relationshipsPresenter) {
        this.relationshipsPresenter = relationshipsPresenter;
    }

    /*
    * Creates a new relationship type
    * @Params relationships Name of the relationship type
    * @Return ResponseEntity with created relationship
    * @Throw DuplicateElementException if relationship name already exists
    */
    @Override
    @PostMapping("/relationship/create/{relationships}")
    public ResponseEntity<ApiResponseOK<Relationships>> createRelationships(@PathVariable String relationships) throws DuplicateElementException {
        Relationships relation = relationshipsPresenter.createRelationships(relationships);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "relationship created",
                relation,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Gets a relationship by ID or name
    * @Params id UUID of the relationship (optional)
    * @Params relation Name of the relationship (optional)
    * @Return ResponseEntity with found relationship
    * @Throw IdNotFoundException if ID is not found
    * @Throw NameNotFoundException if name is not found
    */
    @Override
    @GetMapping("/relationship/get")
    public ResponseEntity<ApiResponseOK<Relationships>> getRelationships(
            @RequestParam (value = "id", required = false) UUID id,
            @RequestParam (value = "relation", required = false) String relation) throws IdNotFoundException, NameNotFoundException {
        Relationships responseRelationship = relationshipsPresenter.getRelationships(id, relation);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "relationship found",
                responseRelationship,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Returns a list of all relationships for admin view
    * @Return ResponseEntity with list of relationships
    */
    @Override
    @GetMapping("/relationship/Admin/list")
    public ResponseEntity<ApiResponseOK<List<Relationships>>> listRelationshipsAdmin() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "list of relationships",
                relationshipsPresenter.listRelationshipsAdmin(),
                HttpStatus.OK.value()
        ));
    }

    /*
    * Returns a public list of all relationship types
    * @Return ResponseEntity with list of relationship DTOs
    */
    @Override
    @GetMapping("/relationship/Public/list")
    public ResponseEntity<ApiResponseOK<List<RelationshipsDTO>>> listRelationshipsPublic() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "list of relationships",
                relationshipsPresenter.listRelationshipsPublic(),
                HttpStatus.OK.value()
        ));
    }

    /*
    * Updates an existing relationship name
    * @Params id UUID of the relationship
    * @Params relation New relationship name
    * @Return ResponseEntity with updated relationship
    * @Throw IdNotFoundException if relationship ID is not found
    */
    @Override
    @PutMapping("/relationship/update/{id}/{relation}")
    public ResponseEntity<ApiResponseOK<Relationships>> updateRelationships(@PathVariable UUID id, @PathVariable String relation) throws IdNotFoundException {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "relationship updated",
                relationshipsPresenter.updateRelationships(id, relation),
                HttpStatus.OK.value()
        ));
    }

}
