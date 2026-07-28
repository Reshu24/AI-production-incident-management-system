import {
  Card,
  CardContent,
  Typography,
  Grid,
} from "@mui/material";

export default function TriageCard({ classification }) {

  return (
    <Card>

      <CardContent>

        <Typography
          variant="h6"
          gutterBottom
        >
          Routing Decision
        </Typography>

        <Grid container spacing={2}>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Category:</b> {classification.category}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Assigned Team:</b> {classification.assignedTeam}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Email:</b> {classification.contactEmail}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Decision:</b> {classification.decision}
            </Typography>
          </Grid>

          <Grid size={{ xs: 6 }}>
            <Typography>
              <b>Confidence:</b>{" "}
              {(classification.confidenceScore * 100).toFixed(2)}%
            </Typography>
          </Grid>

          <Grid size={{ xs: 12 }}>
            <Typography>
              <b>Matched Keywords:</b>
            </Typography>

            <Typography>
              {classification.matchedKeywords}
            </Typography>
          </Grid>

        </Grid>

      </CardContent>

    </Card>
  );
}