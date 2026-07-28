import { useEffect, useState } from "react";
import { Typography, CircularProgress, Box } from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import IncidentTable from "../components/IncidentTable";
import { getAllIncidents } from "../services/incidentService";

import {
  TextField,
  InputAdornment,
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";

export default function Incidents() {

  const [incidents, setIncidents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const filteredIncidents = incidents.filter((incident) =>
    incident.title.toLowerCase().includes(search.toLowerCase()) ||
    incident.description.toLowerCase().includes(search.toLowerCase()) ||
    incident.affectedService.toLowerCase().includes(search.toLowerCase())
);

  useEffect(() => {
    loadIncidents();
  }, []);

  const loadIncidents = async () => {
    try {
      const data = await getAllIncidents();
      setIncidents(data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <DashboardLayout>
      <Typography variant="h4" gutterBottom>
        Incidents
      </Typography>

      {loading ? (
        <Box sx={{ display: "flex", justifyContent: "center", mt: 5 }}>
          <CircularProgress />
        </Box>
      ) : (
        <div style={{ height: 650 }}>
          <TextField
            fullWidth
            placeholder="Search by title, description or service..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            sx={{ mb: 3 }}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            }}
          />
          <IncidentTable incidents={filteredIncidents} />
        </div>
      )}

    </DashboardLayout>
  );
}