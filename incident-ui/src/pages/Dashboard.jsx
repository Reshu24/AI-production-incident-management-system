import { useEffect, useState } from "react";
import { Grid, Typography, CircularProgress, Box } from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import SeverityChart from "../components/SeverityChart";
import StatusChart from "../components/StatusChart";
import StatCard from "../components/StatCard";
import { getDashboardData } from "../services/dashboardService";

export default function Dashboard() {
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const data = await getDashboardData();
      setDashboard(data);
    } catch (error) {
      console.error("Error fetching dashboard:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <DashboardLayout>
      <Typography variant="h4" fontWeight="bold" gutterBottom>
        Dashboard
      </Typography>

      {loading ? (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 5 }}>
          <CircularProgress />
        </Box>
      ) : (

        <>
            <Grid container spacing={3}>
            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 2 }}>
                <StatCard title="Total Incidents" value={dashboard.totalIncidents} />
            </Grid>

            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 2 }}>
                <StatCard title="Open" value={dashboard.openIncidents} />
            </Grid>

            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 2 }}>
                <StatCard title="In Progress" value={dashboard.inProgressIncidents} />
            </Grid>

            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 2 }}>
                <StatCard title="Resolved" value={dashboard.resolvedIncidents} />
            </Grid>

            <Grid size={{ xs: 12, sm: 6, md: 4, lg: 2 }}>
                <StatCard title="Critical" value={dashboard.criticalIncidents} />
            </Grid>

            <Grid size={{ xs: 12, sm: 6, md: 6, lg: 2 }}>
                <StatCard
                title="Knowledge"
                value={dashboard.totalKnowledgeArticles}
                />
            </Grid>
            </Grid>

            <Grid container spacing={3} sx={{ mt: 2 }}>
            <Grid size={{ xs: 12, md: 6 }}>
                <StatusChart data={dashboard.statusChart} />
            </Grid>

            <Grid size={{ xs: 12, md: 6 }}>
                <SeverityChart data={dashboard.severityChart} />
            </Grid>
            </Grid>
        </>
      )}
    </DashboardLayout>
  );
}