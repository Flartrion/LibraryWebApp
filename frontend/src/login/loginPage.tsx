import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  TextField,
} from "@mui/material";
import loginController from "./loginController";
import { useEffect, useState } from "react";
import { GitHub } from "@mui/icons-material";

function LoginPage() {
  const [processing, setProcessing] = useState(false);

  function handleSubmit(e: React.MouseEvent) {
    setProcessing(true);
    const login = (document.getElementById("loginEmail") as HTMLInputElement)
      .value;
    const password = (
      document.getElementById("loginPassword") as HTMLInputElement
    ).value;
    loginController.loginRequest(login, password);
  }

  useEffect(() => {
    loginController.subscribeView("loginPage", setProcessing);
    return () => {
      loginController.unsubscribeView("loginPage");
    };
  });

  return (
    <Box
      sx={{
        marginLeft: "auto",
        marginRight: "auto",
        alignSelf: "center",
        display: "flex",
        flexDirection: "column",
        alignItems: "stretch",
        maxWidth: "800px",
        minWidth: "200px",
      }}
    >
      <Box sx={{ marginBottom: "100px" }} />
      <TextField
        id="loginEmail"
        type="email"
        label="E-mail"
        variant="outlined"
      />
      <TextField
        id="loginPassword"
        type="password"
        label="Password"
        variant="outlined"
      />
      <Button variant="contained" onClick={handleSubmit}>
        PROCEED
      </Button>
      <Button
        onClick={() => {
          location.href = "/login/github";
        }}
      >
        <GitHub />
      </Button>
      <Backdrop open={processing}>
        <CircularProgress />
      </Backdrop>
    </Box>
  );
}
export default LoginPage;
