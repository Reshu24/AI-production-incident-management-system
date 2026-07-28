import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

import { Card, CardContent, Typography } from "@mui/material";

export default function SeverityChart({ data }) {
  return (
    <Card sx={{ height: 400, borderRadius: 3 }}>
      <CardContent>
        <Typography variant="h6" gutterBottom>
          Severity Distribution
        </Typography>

        <ResponsiveContainer width="100%" height={300}>
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="label" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#1976d2" />
          </BarChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );
}