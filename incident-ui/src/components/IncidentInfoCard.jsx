import {
  Card,
  CardContent,
  Typography,
  Grid,
} from "@mui/material";

export default function IncidentInfoCard({ incident }) {

  return (
    <Card>

      <CardContent>

        <Typography
          variant="h6"
          gutterBottom
        >
          Incident Information
        </Typography>

        <Grid container spacing={2}>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Title:</b> {incident.title}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Service:</b> {incident.affectedService}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Severity:</b> {incident.severity}
            </Typography>
          </Grid>

          <Grid size={{ xs: 12 }}>
            <Typography>
              <b>Description:</b>
            </Typography>

            <Typography>
              {incident.description}
            </Typography>
          </Grid>

        </Grid>

      </CardContent>

    </Card>
  );
}