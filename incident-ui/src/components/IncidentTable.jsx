import { DataGrid } from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";
import { Chip } from "@mui/material";

export default function IncidentTable({ incidents }) {

  const navigate = useNavigate();

  const columns = [
    { field: "id", headerName: "ID", width: 80 },

    {
      field: "title",
      headerName: "Title",
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
      field: "status",
      headerName: "Status",
      width: 140,
    },

    {
      field: "affectedService",
      headerName: "Service",
      flex: 1,
    },

    {
      field: "createdAt",
      headerName: "Created",
      width: 180,
    },
  ];

  return (
    <DataGrid
      rows={incidents}
      columns={columns}
      pageSizeOptions={[5,10,20]}
      initialState={{
        pagination: {
          paginationModel: {
            pageSize: 10,
          },
        },
      }}
      onRowClick={(params) => navigate(`/incidents/${params.row.id}`)}
    />
  );
}