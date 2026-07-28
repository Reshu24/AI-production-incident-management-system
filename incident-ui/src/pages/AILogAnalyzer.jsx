import { useState } from "react";

import {
  Typography,
  Grid,
} from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import AIRequestCard from "../components/AIRequestCard";
import AISummaryCard from "../components/AISummaryCard";
import IncidentInfoCard from "../components/IncidentInfoCard";
import TriageCard from "../components/TriageCard";
import MatchTable from "../components/MatchTable";

import { analyseLogs } from "../services/aiService";

export default function AIAnalyzer() {

  const [analysis, setAnalysis] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleAnalyse = async (request) => {

    try {

      setLoading(true);

      const response = await analyseLogs(request);

      setAnalysis(response);

    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }

  };

  return (

    <DashboardLayout>

      <Typography
        variant="h4"
        gutterBottom
      >
        AI Log Analyzer
      </Typography>

      <Grid container spacing={3}>

        <Grid size={{ xs: 12 }}>
          <AIRequestCard
            onAnalyse={handleAnalyse}
            loading={loading}
          />
        </Grid>

        {analysis && (

          <>

            <Grid size={{ xs: 12 }}>
              <AISummaryCard
                summary={analysis.aiSummary}
                rootCause={analysis.rootCause}
              />
            </Grid>

            <Grid size={{ xs: 12 }}>
              <IncidentInfoCard
                incident={analysis.incident}
              />
            </Grid>

            <Grid size={{ xs: 12 }}>
              <TriageCard
                classification={analysis.classification}
              />
            </Grid>

            <Grid size={{ xs: 12 }}>
              <MatchTable
                matches={analysis.similarIncidents}
              />
            </Grid>

          </>

        )}

      </Grid>

    </DashboardLayout>

  );

}