import { useState } from "react";

import {
  Card,
  CardContent,
  Typography,
  Grid,
  TextField,
  MenuItem,
  Button,
  CircularProgress,
} from "@mui/material";

export default function AIRequestCard({
  onAnalyse,
  loading,
}) {

  const [env, setEnv] = useState("QA");
  const [service, setService] = useState("Payment Service");

  const handleSubmit = () => {

    onAnalyse({
      env,
      service,
    });

  };

  return (

    <Card>

      <CardContent>

        <Typography
          variant="h6"
          gutterBottom
        >
          Analyse Application Logs
        </Typography>

        <Grid
          container
          spacing={2}
        >

          <Grid size={{ xs: 12, md: 6 }}>

            <TextField
              select
              label="Environment"
              fullWidth
              value={env}
              onChange={(e) => setEnv(e.target.value)}
            >
              <MenuItem value="DEV">DEV</MenuItem>
              <MenuItem value="QA">QA</MenuItem>
              <MenuItem value="PROD">PROD</MenuItem>
            </TextField>

          </Grid>

          <Grid size={{ xs: 12, md: 6 }}>

            <TextField
              select
              label="Service"
              fullWidth
              value={service}
              onChange={(e) => setService(e.target.value)}
            >
              <MenuItem value="payment-service">
                Payment Service
            </MenuItem>

             <MenuItem value="inventory-service">
                Inventory Service
            </MenuItem>

            <MenuItem value="order-service">
                Order Service
            </MenuItem>

            </TextField>

          </Grid>

          <Grid size={{ xs: 12 }}>

            <Button
              variant="contained"
              fullWidth
              onClick={handleSubmit}
              disabled={loading}
            >

              {loading ? (
                <CircularProgress
                  size={24}
                  color="inherit"
                />
              ) : (
                "Analyze Logs"
              )}

            </Button>

          </Grid>

        </Grid>

      </CardContent>

    </Card>

  );

}