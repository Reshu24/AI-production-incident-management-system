import { DataGrid } from "@mui/x-data-grid";

export default function OwnershipTable({ ownership }) {

  const columns = [
    {
      field: "id",
      headerName: "ID",
      width: 80,
    },
    {
      field: "category",
      headerName: "Category",
      flex: 1,
    },
    {
      field: "teamName",
      headerName: "Assigned Team",
      flex: 1,
    },
    {
      field: "contactEmail",
      headerName: "Contact Email",
      flex: 1.2,
    },
  ];

  return (
    <DataGrid
      rows={ownership}
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