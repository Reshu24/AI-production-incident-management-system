package com.reshmah.incident.dto;

import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {

    private long totalIncidents;
    private long openIncidents;
    private long resolvedIncidents;
    private long inProgressIncidents;
    private long closedIncidents;

    private long criticalIncidents;
    private long lowIncidents;
    private long mediumIncidents;
    private long highIncidents;

    private long totalKnowledgeArticles;
    private long totalTeams;

    private List<ChartData> severityChart;
    private List<ChartData> statusChart;

}
