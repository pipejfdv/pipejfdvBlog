package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Config.JwtUtils;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ChildrenAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.*;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.ChildrenPresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ChildrenContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/funnyMind")
public class ChildrenController implements ChildrenContract.View{
    private final ChildrenPresenter childrenPresenter;
    private final DocumentTypeService documentTypeService;
    private final TceClassificationService tceClassificationService;
    private final UserService userService;
    private final GuardianServices guardianService;
    private final RelationshipsService relationshipsService;
    private final JwtUtils jwtUtils;

    public ChildrenController(ChildrenPresenter childrenPresenter,
                              JwtUtils jwtUtils,
                              DocumentTypeService documentTypeService,
                              TceClassificationService tceClassificationService,
                              UserService userService,
                              GuardianServices guardianService,
                              RelationshipsService relationshipsService) {
        this.childrenPresenter = childrenPresenter;
        this.jwtUtils = jwtUtils;
        this.documentTypeService = documentTypeService;
        this.tceClassificationService = tceClassificationService;
        this.userService = userService;
        this.guardianService = guardianService;
        this.relationshipsService = relationshipsService;
    }

    @Override
    @PostMapping("/children/create/{document}/{tceClassification}")
    public ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> CreateChildren(
            @RequestBody Children children,
            @PathVariable UUID document,
            @PathVariable UUID tceClassification) {
        children.setDocumentType(documentTypeService.getDocumentType(null, document));
        children.setTceClassification(tceClassificationService.getTCEClassification(tceClassification));
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children created successfully",
                childrenPresenter.CreateChildren(children),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/children/getPublic/{childrenId}")
    public ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> GetChildrenPublic(
            @PathVariable String childrenId) {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children retrieved successfully",
                childrenPresenter.getChildrenPublic(UUID.fromString(childrenId)),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/children/getAdmin/{childrenId}")
    public ResponseEntity<ApiResponseOK<ChildrenAdminDTO>> GetChildrenAdmin(
            @PathVariable String childrenId) {
        UUID targetId = UUID.fromString(childrenId);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children retrieved successfully",
                childrenPresenter.getChildrenAdmin(targetId),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @PutMapping("/children/update")
    public ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> UpdateChildren(
            @RequestParam (value = "id", required = false) String childrenId,
            @RequestBody Children children,
            Authentication authentication) {
        UUID targetId = (childrenId == null || childrenId.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(childrenId);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children updated successfully",
                childrenPresenter.updateChildren(children, targetId),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/children/Ad_Me/list")
    public ResponseEntity<ApiResponseOK<List<ChildrenAdminDTO>>> GetAllChildren() {
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children retrieved successfully",
                childrenPresenter.childrenList(),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @GetMapping("/children/public/list")
    public ResponseEntity<ApiResponseOK<List<ChildrenPublicDTO>>> GetChildrenByGuardian(
            Authentication authentication) {
        UUID guardianId = userService.getUser(jwtUtils.extractUserId(authentication)).getGuardian().getId();
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children retrieved successfully",
                childrenPresenter.getChildrenByGuardian(guardianId),
                HttpStatus.OK.value()
        ));
    }

    @Override
    @DeleteMapping("/children/deleted")
    public ResponseEntity<ApiResponseOK<String>> DeleteChildren(
            @RequestParam (value = "id", required = false) String childrenId,
            Authentication authentication) {
        UUID targetId = (childrenId == null || childrenId.isBlank())
                ? jwtUtils.extractUserId(authentication)
                : UUID.fromString(childrenId);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Children deleted successfully",
                childrenPresenter.deleteChildren(targetId),
                HttpStatus.OK.value()
        ));
    }
}
