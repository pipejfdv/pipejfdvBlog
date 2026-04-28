package com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildDTO {
    private UUID id;
    private String names;
    private String lastNames;
    private String TCE;
}
