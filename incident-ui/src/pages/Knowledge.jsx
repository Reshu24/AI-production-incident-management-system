import { useEffect, useState } from "react";

import {
  Typography,
  CircularProgress,
  Box,
} from "@mui/material";

import DashboardLayout from "../components/DashboardLayout";
import KnowledgeTable from "../components/KnowledgeTable";

import { getAllKnowledge } from "../services/knowledgeService";

export default function Knowledge() {

  const [knowledge, setKnowledge] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadKnowledge();
  }, []);

  const loadKnowledge = async () => {
    try {

      const data = await getAllKnowledge();
      setKnowledge(data);

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
        Knowledge Base
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
          <KnowledgeTable knowledge={knowledge} />
        </div>

      )}

    </DashboardLayout>
  );
}