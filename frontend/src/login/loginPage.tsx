import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  TextField,
} from "@mui/material";
import loginController from "./loginController";
import React, { useEffect, useState } from "react";
import { GitHub } from "@mui/icons-material";

function LoginPage() {
  const [processing, setProcessing] = useState(false);

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    setProcessing(true);
    const login = (document.getElementById("loginEmail") as HTMLInputElement)
      .value;
    const password = (
      document.getElementById("loginPassword") as HTMLInputElement
    ).value;
    loginController.loginRequest(login, password);
  };

  useEffect(() => {
    loginController.subscribeView("loginPage", setProcessing);
    return () => {
      loginController.unsubscribeView("loginPage");
    };
  });

  return (
    <form onSubmit={handleSubmit} noValidate>
      <Box
        sx={{
          marginLeft: "auto",
          marginRight: "auto",
          alignSelf: "center",
          display: "flex",
          flexDirection: "column",
          alignItems: "stretch",
          justifyContent: "space-evenly",
          minHeight: "40vh",
          maxWidth: "800px",
          minWidth: "200px",
        }}
      >
        <TextField
          id="loginEmail"
          type="email"
          autoComplete="email"
          label="E-mail"
          variant="outlined"
          required
        />
        <TextField
          id="loginPassword"
          type="password"
          autoComplete="current-password"
          label="Password"
          variant="outlined"
          required
        />
        <Button type="submit" variant="contained">
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
    </form>
  );
}
export default LoginPage;
