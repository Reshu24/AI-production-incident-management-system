package com.reshmah.incident.ai;

import com.reshmah.incident.dto.AIAnalysisResponse;
import com.reshmah.incident.dto.IncidentRequest;
import com.reshmah.incident.dto.TriageResult;
import com.reshmah.incident.ai.OllamaIncidentDTO;
import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.service.TriageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIAnalysisService {

    private final LogService logService;
    private final OllamaService ollamaService;
    private final TriageService triageService;

    public AIAnalysisResponse analyseLogs(String environment, String service) throws Exception {

        System.out.println("Step 1 - Reading logs");
        String logs = logService.readLogsFromCloudWatch(environment, service);
        System.out.println("Log length = " + logs.length());

        System.out.println("Step 2 - Calling Ollama");
        OllamaIncidentDTO aiIncident = ollamaService.analyseLogs(logs);
        System.out.println("Step 3 - AI returned");

        IncidentRequest request = new IncidentRequest();

        request.setTitle(aiIncident.getTitle());
        request.setDescription(aiIncident.getDescription());
        request.setSeverity(IncidentEntity.Severity.valueOf(aiIncident.getSeverity()));
        request.setAffectedService(aiIncident.getAffectedService());

        System.out.println("Step 4 - Calling triage");
        TriageResult result =
                triageService.triageIncident(request);

        System.out.println("Step 5 - Done");
        return new AIAnalysisResponse(
                result.getIncident(),
                result.getClassification(),
                result.getSimilarIncidents(),
                aiIncident.getRootCause(),
                aiIncident.getAiSummary()
        );

    }

}