import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";

import {
  Box,
  Card,
  CardContent,
  Typography,
  TextField,
  Button,
  Snackbar,
  Alert,
} from "@mui/material";

export default function Login() {

  // React State
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // Snackbar State
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [severity, setSeverity] = useState("success");

  const navigate = useNavigate();

  const handleLogin = async () => {

    try {

      const response = await login(username, password);

      console.log(response);

      // Save JWT Token
      localStorage.setItem("token", response.token);

      // Success Banner
      setSeverity("success");
      setMessage("Login Successful");
      setOpen(true);

      // Wait 1 second and redirect
      setTimeout(() => {
        navigate("/dashboard");
      }, 1000);

    } catch (error) {

      console.error(error);

      setSeverity("error");
      setMessage("Invalid Username or Password");
      setOpen(true);

    }

  };

  return (

    <Box
      sx={{
        height: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#f4f6f8"
      }}
    >

      <Card sx={{ width: 420 }}>

        <CardContent>

          <Typography
            variant="h5"
            textalign="center"
            gutterBottom
          >
            Incident Management Portal
          </Typography>

          <TextField
            label="Username"
            fullWidth
            margin="normal"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
          />

          <TextField
            label="Password"
            type="password"
            fullWidth
            margin="normal"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />

          <Button
            variant="contained"
            fullWidth
            sx={{ mt: 2 }}
            onClick={handleLogin}
          >
            Login
          </Button>

        </CardContent>

      </Card>

      <Snackbar
        open={open}
        autoHideDuration={3000}
        onClose={() => setOpen(false)}
      >

        <Alert
          severity={severity}
          variant="filled"
          onClose={() => setOpen(false)}
        >
          {message}
        </Alert>

      </Snackbar>

    </Box>

  );

}