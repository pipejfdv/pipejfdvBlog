package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.ChildrenRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.TceClassificationRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.TCEClassificationContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TceClassificationService implements TCEClassificationContract.Model{
    private final TceClassificationRepository tceClassificationRepository;
    private final ChildrenRepository childrenRepository;

    public TceClassificationService(TceClassificationRepository tceClassificationRepository, ChildrenRepository childrenRepository) {
        this.tceClassificationRepository = tceClassificationRepository;
        this.childrenRepository = childrenRepository;
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

    @Override
    public boolean updateTCEClassificationByChildren(UUID idChildren, UUID idTceClassification) throws IdNotFoundException {
        Children children = childrenRepository.findById(idChildren)
                .orElseThrow(() -> new IdNotFoundException(idChildren));
        TceClassification classification = tceClassificationRepository.findById(idTceClassification)
                .orElseThrow(()->new IdNotFoundException(idTceClassification));
        children.setTceClassification(classification);
        try {
            childrenRepository.save(children);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
