package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.TceClassificationRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.TCEClassificationContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TceClassificationService implements TCEClassificationContract.Model{
    private final TceClassificationRepository tceClassificationRepository;

    public TceClassificationService(TceClassificationRepository tceClassificationRepository) {
        this.tceClassificationRepository = tceClassificationRepository;
    }

    @Override
    public TceClassification getTCEClassification(UUID id) throws IdNotFoundException {
        return tceClassificationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    public List<TceClassification> getTCEClassifications() {
        return tceClassificationRepository.findAll();
    }

    @Override
    public TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException {
        TceClassification tceClassificationToUpdate = tceClassificationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        tceClassificationToUpdate.setClassification(tceClassification);
        return tceClassificationRepository.save(tceClassificationToUpdate);
    }
}
