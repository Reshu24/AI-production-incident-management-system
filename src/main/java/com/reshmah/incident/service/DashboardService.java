package com.reshmah.incident.service;

import com.reshmah.incident.dto.ChartData;
import com.reshmah.incident.dto.DashboardResponse;
import com.reshmah.incident.entity.IncidentEntity;
import com.reshmah.incident.repository.IncidentRepository;
import com.reshmah.incident.repository.KnowledgeEntryRepository;
import com.reshmah.incident.repository.OwnershipMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IncidentRepository incidentRepository;
    private final KnowledgeEntryRepository knowledgeRepository;
    private final OwnershipMappingRepository ownershipRepository;

    public DashboardResponse getDashboard() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalIncidents(incidentRepository.count());

        response.setCriticalIncidents(
                incidentRepository.countBySeverity(IncidentEntity.Severity.CRITICAL));

        response.setHighIncidents(
                incidentRepository.countBySeverity(IncidentEntity.Severity.HIGH));

        response.setMediumIncidents(
                incidentRepository.countBySeverity(IncidentEntity.Severity.MEDIUM));

        response.setLowIncidents(
                incidentRepository.countBySeverity(IncidentEntity.Severity.LOW));

        response.setOpenIncidents(
                incidentRepository.countByStatus(IncidentEntity.Status.OPEN));

        response.setInProgressIncidents(
                incidentRepository.countByStatus(IncidentEntity.Status.IN_PROGRESS));

        response.setResolvedIncidents(
                incidentRepository.countByStatus(IncidentEntity.Status.RESOLVED));

        response.setClosedIncidents(
                incidentRepository.countByStatus(IncidentEntity.Status.CLOSED));

        response.setTotalKnowledgeArticles(knowledgeRepository.count());
        response.setTotalTeams(ownershipRepository.count());

        response.setSeverityChart(buildSeverityChart(response));
        response.setStatusChart(buildStatusChart(response));

        return response;
    }

    private List<ChartData> buildStatusChart(DashboardResponse response) {

        return List.of(
                new ChartData("Open", response.getOpenIncidents()),
                new ChartData("In Progress", response.getInProgressIncidents()),
                new ChartData("Resolved", response.getResolvedIncidents()),
                new ChartData("Closed", response.getClosedIncidents())
        );
    }

    private List<ChartData> buildSeverityChart(DashboardResponse response) {

        return List.of(
                new ChartData("Critical", response.getCriticalIncidents()),
                new ChartData("High", response.getHighIncidents()),
                new ChartData("Medium", response.getMediumIncidents()),
                new ChartData("Low", response.getLowIncidents())
        );
    }
}
