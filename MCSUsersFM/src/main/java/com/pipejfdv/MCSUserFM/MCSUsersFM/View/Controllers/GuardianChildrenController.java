package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.CreationGuardianChildrenDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.GuardianChildrenPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianChildrenContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class GuardianChildrenController implements GuardianChildrenContract.View{
    private final GuardianChildrenPresenter guardianChildrenPresenter;

    public GuardianChildrenController(GuardianChildrenPresenter guardianChildrenPresenter) {
        this.guardianChildrenPresenter = guardianChildrenPresenter;
    }

    /*
    * Creates a relationship between a guardian and a children
    * @Params packsIds DTO with guardian and children IDs
    * @Return ResponseEntity with created relation ID
    */
    @Override
    @PostMapping("/guardianChildren/create")
    public ResponseEntity<ApiResponseOK<String>> createGuardianChildren(@RequestBody CreationGuardianChildrenDTO packsIds) {
        UUID relation = guardianChildrenPresenter.createGuardianChildren(packsIds).getId();
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Create relation successfully",
                "id: "+relation,
                HttpStatus.OK.value()
                )
        );
    }
    /*
    * Updates the relationship type between a guardian and a children
    * @Params guardianChildId UUID of the guardian-children relation
    * @Params relationshipId UUID of the new relationship type
    * @Return ResponseEntity with updated relation ID
    */
    @Override
    @PutMapping("/guardianChildren/update/{guardianChildId}/{relationshipId}")
    public ResponseEntity<ApiResponseOK<String>> updateGuardianChildren(
            @PathVariable UUID guardianChildId,
            @PathVariable UUID relationshipId) {
        UUID relation = guardianChildrenPresenter.updateGuardianChildren(guardianChildId, relationshipId).getId();
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Update relation successfully",
                "relation: " + relation,
                HttpStatus.OK.value()
        ));
    }
}
