package com.remcoil.timetracker.progress.admin;

import com.remcoil.timetracker.progress.core.ProgressService;
import com.remcoil.timetracker.progress.core.models.Progress;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
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
    public @ResponseBody Map<LocalDate, List<Progress>> getUserProgress(
            @PathVariable UUID id,
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate end
    ) {
        return progressService.getProgressByUser(id, start, end);
    }

    @GetMapping("/general")
    public @ResponseBody List<Progress> getGeneralProgress(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "d.MM.yyyy") LocalDate end
    ) {
        return progressService.getGeneralProgress(null, start, end);
    }
}
