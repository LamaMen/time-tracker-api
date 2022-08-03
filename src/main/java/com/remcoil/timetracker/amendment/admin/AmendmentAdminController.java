package com.remcoil.timetracker.amendment.admin;

import com.remcoil.timetracker.amendment.core.domain.AmendmentUseCase;
import com.remcoil.timetracker.core.MessageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/admin/amendment")
@SecurityRequirement(name = "time-tracker")
public class AmendmentAdminController {
    private final AmendmentUseCase amendmentUseCase;

    public AmendmentAdminController(AmendmentUseCase amendmentUseCase) {
        this.amendmentUseCase = amendmentUseCase;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addAmendment(@RequestBody AmendmentData amendment) {
        amendmentUseCase.add(amendment);
        return ResponseEntity.ok(new MessageResponse("Amendment added"));
    }
}
