import { useEffect, useState } from "react";

import {
  Typography,
  CircularProgress,
  Box,
} from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import OwnershipTable from "../components/OwnershipTable";

import { getAllOwnershipMaps } from "../services/ownershipService";

export default function Ownership() {

  const [ownership, setOwnership] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadOwnership();
  }, []);

  const loadOwnership = async () => {
    try {
      const data = await getAllOwnershipMaps();
      setOwnership(data);
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
        Ownership Mapping
      </Typography>

      {loading ? (
        <Box
          display="flex"
          justifyContent="center"
          mt={5}
        >
          <CircularProgress />
        </Box>
      ) : (
        <div style={{ height: 650 }}>
          <OwnershipTable ownership={ownership} />
        </div>
      )}

    </DashboardLayout>
  );
}