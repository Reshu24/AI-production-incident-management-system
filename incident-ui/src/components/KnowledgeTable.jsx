import { DataGrid } from "@mui/x-data-grid";
import { Chip } from "@mui/material";

export default function KnowledgeTable({ knowledge }) {

  const columns = [
    {
      field: "id",
      headerName: "ID",
      width: 80,
    },

    {
      field: "title",
      headerName: "Title",
      flex: 1,
    },

    {
      field: "affectedService",
      headerName: "Service",
      flex: 1,
    },

    {
      field: "severity",
      headerName: "Severity",
      width: 140,
      renderCell: (params) => {
        const color = {
          LOW: "success",
          MEDIUM: "warning",
          HIGH: "error",
          CRITICAL: "secondary",
        }[params.value];

        return (
          <Chip
            label={params.value}
            color={color}
            size="small"
          />
        );
      },
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

    {
      field: "resolvedAt",
      headerName: "Resolved At",
      width: 180,
    },
  ];

  return (
    <DataGrid
      rows={knowledge}
      columns={columns}
      pageSizeOptions={[5, 10, 20]}
      initialState={{
        pagination: {
          paginationModel: {
            pageSize: 10,
          },
        },
      }}
    />
  );
}