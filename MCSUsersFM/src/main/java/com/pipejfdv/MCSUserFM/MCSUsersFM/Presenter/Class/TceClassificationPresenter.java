package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.TceClassificationService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.TCEClassificationContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TceClassificationPresenter implements TCEClassificationContract.Presenter{
    private final TceClassificationService tceClassificationService;

    public TceClassificationPresenter(TceClassificationService tceClassificationService) {
        this.tceClassificationService = tceClassificationService;
    }

    @Override
    public TceClassification getTCEClassification(UUID id) throws IdNotFoundException {
        return tceClassificationService.getTCEClassification(id);
    }

    @Override
    public List<TceClassification> getTCEClassifications() {
        return tceClassificationService.getTCEClassifications();
    }

    @Override
    public TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException {
        return tceClassificationService.updateTCEClassification(id, tceClassification);
    }
}
