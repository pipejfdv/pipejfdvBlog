package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.TceClassificationDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.TceClassificationService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.TCEClassificationContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
* Presenter for TCE Classification operations
* Acts as intermediary between controller and TceClassificationService
*/
@Component
public class TceClassificationPresenter implements TCEClassificationContract.Presenter{
    private final TceClassificationService tceClassificationService;

    public TceClassificationPresenter(TceClassificationService tceClassificationService) {
        this.tceClassificationService = tceClassificationService;
    }

    /*
    * Retrieves a TCE classification by ID
    * @Params id The UUID of the classification
    * @Return TceClassification The found classification
    * @Throw IdNotFoundException if classification not found
    */
    @Override
    public TceClassification getTCEClassification(UUID id) throws IdNotFoundException {
        return tceClassificationService.getTCEClassification(id);
    }

    /*
    * Retrieves all TCE classifications
    * @Return List of all classifications
    */
    @Override
    public List<TceClassificationDTO> getTCEClassifications() {
        return tceClassificationService.getTCEClassifications().stream()
                .map(tce -> new TceClassificationDTO(
                        tce.getId(),
                        tce.getClassification()
                )).toList();
    }

    /*
    * Updates a TCE classification
    * @Params id The UUID of the classification to update
    * @Params tceClassification The new classification string
    * @Return TceClassification The updated classification
    * @Throw IdNotFoundException if classification not found
    */
    @Override
    public TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException {
        return tceClassificationService.updateTCEClassification(id, tceClassification);
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
        return tceClassificationService.updateTCEClassificationByChildren(idChildren, idTceClassification);
    }
}
