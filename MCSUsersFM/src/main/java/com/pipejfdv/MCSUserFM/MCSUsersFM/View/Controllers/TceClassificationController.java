package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.TceClassificationDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.TceClassificationPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.TCEClassificationContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/funnyMind")
@RestController
public class TceClassificationController implements TCEClassificationContract.View{
    private final TceClassificationPresenter tceClassificationPresenter;

    public TceClassificationController(TceClassificationPresenter tceClassificationPresenter) {
        this.tceClassificationPresenter = tceClassificationPresenter;
    }

    /*
    * Gets a single TCE classification by ID
    * @Params id UUID of the TCE classification
    * @Return ResponseEntity with TCE classification data
    */
    @Override
    @GetMapping("/tceClassification/type/{id}")
    public ResponseEntity<ApiResponseOK<TceClassification>> getTCEClassification(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponseOK<>("Type of classification TCE", tceClassificationPresenter.getTCEClassification(id), HttpStatus.OK.value()));
    }

    /*
    * Returns a list of all TCE classifications
    * @Return ResponseEntity with list of TCE classifications
    */
    @Override
    @GetMapping("/tceClassification/list")
    public ResponseEntity<ApiResponseOK<List<TceClassificationDTO>>> TCEClassifications() {
        return ResponseEntity.ok(new ApiResponseOK<>("List of classification TCE", tceClassificationPresenter.getTCEClassifications(), HttpStatus.OK.value()));
    }

    /*
    * Updates the name of a TCE classification
    * @Params id UUID of the TCE classification
    * @Params classification New classification name
    * @Return ResponseEntity with updated TCE classification
    */
    @Override
    @PutMapping("/tceClassification/update/{id}/{classification}")
    public ResponseEntity<ApiResponseOK<TceClassification>> updateTCEClassification(@PathVariable UUID id,@PathVariable String classification) {
        return ResponseEntity.ok(new ApiResponseOK<>("Update of classification TCE", tceClassificationPresenter.updateTCEClassification(id, classification), HttpStatus.OK.value()));
    }

    /*
    * Updates a children's TCE classification assignment
    * @Params idChildren UUID of the children
    * @Params idTceClassification UUID of the new TCE classification
    * @Return ResponseEntity with boolean update result
    */
    @Override
    @PutMapping("/tceClassification/updateByChildren/{idChildren}/{idTceClassification}")
    public ResponseEntity<ApiResponseOK<Boolean>> updateTCEClassificationByChildren(
            @PathVariable UUID idChildren,
            @PathVariable UUID idTceClassification) {
        return ResponseEntity.ok(new ApiResponseOK<>("Update of classification TCE by children",
                tceClassificationPresenter.updateTCEClassificationByChildren(idChildren, idTceClassification),
                HttpStatus.OK.value()
        ));
    }
}
