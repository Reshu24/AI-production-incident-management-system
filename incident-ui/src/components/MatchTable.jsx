import {
  Card,
  CardContent,
  Typography,
} from "@mui/material";

import { DataGrid } from "@mui/x-data-grid";

export default function MatchTable({ matches }) {

  const columns = [
    {
      field: "knowledgeEntryId",
      headerName: "Knowledge ID",
      width: 140,
    },
    {
      field: "title",
      headerName: "Title",
      flex: 1,
    },
    {
      field: "similarityScore",
      headerName: "Similarity",
      width: 150,
      renderCell: (params) =>
        `${(params.value * 100).toFixed(2)}%`,
    },
    {
      field: "rootCause",
      headerName: "Root Cause",
      flex: 1,
    },
    {
      field: "resolutionNotes",
      headerName: "Resolution",
      flex: 1,
    },
  ];

  return (
    <Card>

      <CardContent>

        <Typography
          variant="h6"
          gutterBottom
        >
          Similar Historical Incidents
        </Typography>

        <div style={{ height: 400 }}>
          <DataGrid
            rows={matches}
            columns={columns}
            getRowId={(row) => row.knowledgeEntryId}
            pageSizeOptions={[5]}
          />
        </div>

      </CardContent>

    </Card>
  );
}