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
