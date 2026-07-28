import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function Topbar() {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <AppBar
      position="static"
      color="primary"
      elevation={1}
    >
      <Toolbar>

        <Typography
          variant="h6"
          sx={{ flexGrow: 1 }}
        >
          Incident Management System
        </Typography>

        <Box sx={{ mr: 2 }}>
          ADMIN
        </Box>

        <Button
          color="inherit"
          onClick={handleLogout}
        >
          Logout
        </Button>

      </Toolbar>
    </AppBar>
  );
}