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

/*
* Service for managing TCE Classification entities
* Handles retrieval, update, and assignment to children
*/
@Service
public class TceClassificationService implements TCEClassificationContract.Model{
    private final TceClassificationRepository tceClassificationRepository;
    private final ChildrenRepository childrenRepository;

    public TceClassificationService(TceClassificationRepository tceClassificationRepository, ChildrenRepository childrenRepository) {
        this.tceClassificationRepository = tceClassificationRepository;
        this.childrenRepository = childrenRepository;
    }

    /*
    * Retrieves a TCE classification by its ID
    * @Params id The UUID of the classification
    * @Return TceClassification The found classification
    * @Throw IdNotFoundException if classification not found
    */
    @Override
    public TceClassification getTCEClassification(UUID id) throws IdNotFoundException {
        return tceClassificationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    /*
    * Retrieves all TCE classifications
    * @Return List of all classifications
    */
    @Override
    public List<TceClassification> getTCEClassifications() {
        return tceClassificationRepository.findAll();
    }

    /*
    * Updates the name of a TCE classification
    * @Params id The UUID of the classification to update
    * @Params tceClassification The new classification string
    * @Return TceClassification The updated classification
    * @Throw IdNotFoundException if classification not found
    */
    @Override
    public TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException {
        TceClassification tceClassificationToUpdate = tceClassificationRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        tceClassificationToUpdate.setClassification(tceClassification);
        return tceClassificationRepository.save(tceClassificationToUpdate);
    }

    /*
    * Assigns a TCE classification to a children record
    * @Params idChildren The UUID of the children
    * @Params idTceClassification The UUID of the classification to assign
    * @Return boolean True if assignment was successful
    * @Throw IdNotFoundException if children or classification not found
    */
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
