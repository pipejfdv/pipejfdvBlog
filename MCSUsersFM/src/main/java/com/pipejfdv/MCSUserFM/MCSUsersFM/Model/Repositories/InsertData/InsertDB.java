package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.InsertData;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.AccountType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.AccountTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.DocumentTypeRepository;
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

    public InsertDB(AccountTypeRepository accountTypeRepository, DocumentTypeRepository documentTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountTypeRepository.count() == 0) {
            accountTypeRepository.saveAll(List.of(
                    new AccountType("Demo User"),
                    new AccountType("Premium User"),
                    new AccountType("Admin"),
                    new AccountType("Medic")
            ));
        }
        if (documentTypeRepository.count() == 0) {
            documentTypeRepository.saveAll(List.of(
                    new DocumentType("Cédula Ciudadania"),
                    new DocumentType("Cedula Extranjería"),
                    new DocumentType("Tarjeta de Identidad"),
                    new DocumentType("Permiso Transitorio")
            ));
        }
    }
}
