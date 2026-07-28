import { Card, CardContent, Typography, Box } from "@mui/material";

export default function StatCard({ title, value, color = "primary.main" }) {
  return (
    <Card sx={{ height: "100%", borderRadius: 3 }}>
      <CardContent>
        <Typography color="text.secondary" gutterBottom>
          {title}
        </Typography>

        <Box sx={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
          <Typography variant="h4" fontWeight="bold" color={color}>
            {value}
          </Typography>
        </Box>
      </CardContent>
    </Card>
  );
}