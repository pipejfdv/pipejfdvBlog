package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
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

    @Override
    @GetMapping("/tceClassification/{id}")
    public ResponseEntity<ApiResponseOK<TceClassification>> getTCEClassification(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponseOK<>("Type of classification TCE", tceClassificationPresenter.getTCEClassification(id), HttpStatus.OK.value()));
    }

    @Override
    @GetMapping("/tceClassification/list")
    public ResponseEntity<ApiResponseOK<List<TceClassification>>> TCEClassifications() {
        return ResponseEntity.ok(new ApiResponseOK<>("List of classification TCE", tceClassificationPresenter.getTCEClassifications(), HttpStatus.OK.value()));
    }

    @Override
    @PutMapping("/tceClassification/{id}/{classification}")
    public ResponseEntity<ApiResponseOK<TceClassification>> updateTCEClassification(@PathVariable UUID id,@PathVariable String classification) {
        return ResponseEntity.ok(new ApiResponseOK<>("Update of classification TCE", tceClassificationPresenter.updateTCEClassification(id, classification), HttpStatus.OK.value()));
    }
}
