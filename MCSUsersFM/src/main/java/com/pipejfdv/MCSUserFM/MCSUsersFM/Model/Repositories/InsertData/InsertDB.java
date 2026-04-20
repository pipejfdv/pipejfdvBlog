package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.InsertData;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.DocumentTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.RelationshipsRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.TceClassificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsertDB implements CommandLineRunner {
    /*
    * this component takes care of insert data in tables predefined
    * */
    private final AccountTypeRepository accountTypeRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final RelationshipsRepository relationshipsRepository;
    private final TceClassificationRepository tceClassificationRepository;

    public InsertDB(AccountTypeRepository accountTypeRepository,
                    DocumentTypeRepository documentTypeRepository,
                    RelationshipsRepository relationshipsRepository,
                    TceClassificationRepository tceClassificationRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.relationshipsRepository = relationshipsRepository;
        this.tceClassificationRepository = tceClassificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountTypeRepository.count() == 0) {
            accountTypeRepository.saveAll(List.of(
                    new AccountType("DemoUser"),
                    new AccountType("PremiumUser"),
                    new AccountType("Admin"),
                    new AccountType("Medic"),
                    new AccountType("Analyst")
            ));
        }
        if (documentTypeRepository.count() == 0) {
            documentTypeRepository.saveAll(List.of(
                    new DocumentType("Cédula Ciudadanía"),
                    new DocumentType("Cedula Extranjería"),
                    new DocumentType("Tarjeta de Identidad"),
                    new DocumentType("Permiso Transitorio")
            ));
        }
        if (relationshipsRepository.count()==0){
            relationshipsRepository.saveAll(List.of(
                    new Relationships("Padre"),
                    new Relationships("Madre"),
                    new Relationships("Tio"),
                    new Relationships("Tia"),
                    new Relationships("Tutor"),
                    new Relationships("Padrastro"),
                    new Relationships("Madrastra"),
                    new Relationships("Hermano"),
                    new Relationships("Hermana")
            ));
        }
        if(tceClassificationRepository.count() == 0){
            tceClassificationRepository.saveAll(List.of(
                    new TceClassification("Inicial"),
                    new TceClassification("Basico"),
                    new TceClassification("Intermedio"),
                    new TceClassification("Avanzado")
            ));
        }
    }
}
