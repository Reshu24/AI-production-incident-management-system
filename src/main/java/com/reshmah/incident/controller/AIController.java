package com.reshmah.incident.controller;

import com.reshmah.incident.ai.AIAnalysisService;
import com.reshmah.incident.dto.AIAnalysisResponse;
import com.reshmah.incident.dto.AIAnalysisRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class AIController {

    private final AIAnalysisService aiIncidentService;

    @PostMapping("/analyse")
    public AIAnalysisResponse analyse(@RequestBody AIAnalysisRequest request) throws Exception {

        return aiIncidentService.analyseLogs(
                request.getEnv(),
                request.getService()
        );

    }

}