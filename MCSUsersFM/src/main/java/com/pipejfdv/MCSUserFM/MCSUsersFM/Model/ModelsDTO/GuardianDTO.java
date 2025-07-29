package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;


public class GuardianDTO {
    final private String name;
    final private String lastname;
    final private String phone;
    final private String biography;
    private final String email;
    private final String username;
    private final String typeAccountName;
    private final String document;
    private final String documentType;

    /*DTO for Admin*/
    public GuardianDTO(String name,
                       String lastname,
                       String phone,
                       String biography,
                       String username,
                       String email,
                       String typeAccountName,
                       String document,
                       String typeDocument) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.biography = biography;
        this.username = username;
        this.email = email;
        this.typeAccountName = typeAccountName;
        this.document = document;
        this.documentType = typeDocument;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getBiography() {
        return biography;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getTypeAccountName() {
        return typeAccountName;
    }
    public String getDocument() {
        return document;
    }
    public String getDocumentType() {
        return documentType;
    }
}
