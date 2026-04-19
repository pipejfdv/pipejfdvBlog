package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
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

    @Override
    @GetMapping("/relationship/list")
    public ResponseEntity<ApiResponseOK<List<String>>> listRelationships() {
        List<String> listRelations = relationshipsPresenter.listRelationships().stream()
                .map(Relationships :: getRelationship).toList();
        return ResponseEntity.ok(new ApiResponseOK<>(
                "list of relationships",
                listRelations,
                HttpStatus.OK.value()
        ));
    }

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
