package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ChildProgres;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ProgressParameterPackage;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildProgressDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.ChildProgresService;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class ChildProgressController {
    private final ChildProgresService childProgresService;

    public ChildProgressController(ChildProgresService childProgresService) {
        this.childProgresService = childProgresService;
    }

    @PutMapping("/progress")
    public ResponseEntity<ResponseOk<ChildProgressDTO>> chargeChildProgres(
            @RequestBody ProgressParameterPackage parameterPackage) throws IdNotFound {
        boolean confirmation = childProgresService.existsChildProgres(parameterPackage.getChildProgres().getChildrenId(),
                parameterPackage.getChildProgres().getCategoryOfGame().getId());
        if (confirmation){
            return ResponseEntity.ok(new ResponseOk<>(
                    "update success",
                    childProgresService.updateChildProgres(parameterPackage),
                    HttpStatus.OK.value()
            ));
        }
        else{
         return ResponseEntity.ok(new ResponseOk<>(
                 "create success",
                 childProgresService.createChildProgres(parameterPackage),
                 HttpStatus.OK.value()
         ));
        }
    }

}
