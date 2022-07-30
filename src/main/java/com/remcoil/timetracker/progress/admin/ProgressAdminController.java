package com.remcoil.timetracker.progress.admin;

import com.remcoil.timetracker.progress.core.ProgressService;
import com.remcoil.timetracker.progress.core.models.Progress;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/admin/progress")
@SecurityRequirement(name = "time-tracker")
public class ProgressAdminController {
    private final ProgressService progressService;

    public ProgressAdminController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{id}")
    public @ResponseBody Map<LocalDate, List<Progress>> getUserProgress(@PathVariable UUID id) {
        return progressService.getProgressByUser(id);
    }
}
