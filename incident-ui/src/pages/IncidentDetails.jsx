import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
  Typography,
  CircularProgress,
  Box,
  Grid,
  Button,
} from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import IncidentInfoCard from "../components/IncidentInfoCard";
import TriageCard from "../components/TriageCard";
import MatchTable from "../components/MatchTable";

import {
  getIncidentById,
  triageIncident,
  matchIncident,
} from "../services/incidentService";

export default function IncidentDetails() {
  const { id } = useParams();

  const [incident, setIncident] = useState(null);
  const [triage, setTriage] = useState(null);
  const [matches, setMatches] = useState([]);

  const [loading, setLoading] = useState(true);
  const [triageLoading, setTriageLoading] = useState(false);
  const [matchLoading, setMatchLoading] = useState(false);

  useEffect(() => {
    loadIncident();
  }, []);

  const loadIncident = async () => {
    try {
      const incidentData = await getIncidentById(id);
      setIncident(incidentData);
    } catch (error) {
      console.error("Error loading incident:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleRunTriage = async () => {
    if (!incident) return;

    setTriageLoading(true);

    try {
      const request = {
        title: incident.title,
        description: incident.description,
        affectedService: incident.affectedService,
        severity: incident.severity,
      };

      const response = await triageIncident(request);
      setTriage(response);
    } catch (error) {
      console.error("Error running triage:", error);
    } finally {
      setTriageLoading(false);
    }
  };

  const handleMatch = async () => {
    if (!incident) return;

    setMatchLoading(true);

    try {
      const response = await matchIncident(incident.description);
      setMatches(response);
    } catch (error) {
      console.error("Error finding matches:", error);
    } finally {
      setMatchLoading(false);
    }
  };

  if (loading) {
    return (
      <DashboardLayout>
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            mt: 5,
          }}
        >
          <CircularProgress />
        </Box>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <Typography variant="h4" gutterBottom>
        Incident Details
      </Typography>

      <Grid container spacing={3}>
        <Grid size={{ xs: 12 }}>
          <IncidentInfoCard incident={incident} />
        </Grid>

        <Grid
          size={{ xs: 12 }}
          sx={{
            display: "flex",
            gap: 2,
          }}
        >
          <Button
            variant="contained"
            onClick={handleRunTriage}
            disabled={triageLoading}
          >
            {triageLoading ? "Running..." : "Run Triage"}
          </Button>

          <Button
            variant="outlined"
            onClick={handleMatch}
            disabled={matchLoading}
          >
            {matchLoading ? "Finding..." : "Find Similar Incidents"}
          </Button>
        </Grid>

        {triage && (
          <Grid size={{ xs: 12 }}>
            <TriageCard classification={triage.classification} />
          </Grid>
        )}

        {matches.length > 0 && (
          <Grid size={{ xs: 12 }}>
            <MatchTable matches={matches} />
          </Grid>
        )}
      </Grid>
    </DashboardLayout>
  );
}