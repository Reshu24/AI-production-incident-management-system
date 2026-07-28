import {
  Card,
  CardContent,
  Typography,
  Divider,
  Box,
} from "@mui/material";

export default function AISummaryCard({
  summary,
  rootCause,
}) {

  return (

    <Card>

      <CardContent>

        <Typography
          variant="h6"
          gutterBottom
        >
          🤖 AI Analysis
        </Typography>

        <Divider sx={{ mb: 2 }} />

        <Box mb={3}>

          <Typography
            variant="subtitle2"
            color="text.secondary"
            gutterBottom
          >
            AI Summary
          </Typography>

          <Typography>
            {summary}
          </Typography>

        </Box>

        <Box>

          <Typography
            variant="subtitle2"
            color="text.secondary"
            gutterBottom
          >
            Root Cause
          </Typography>

          <Typography color="error">
            {rootCause}
          </Typography>

        </Box>

      </CardContent>

    </Card>

  );

}