import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  TextField,
} from "@mui/material";
import loginController from "./loginController";
import { useEffect, useState } from "react";

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
        helperText="NETIZENS HAVE AN OBLIGATION TO REPORT THEIR RESIDENCE STATUS"
      />
      <TextField
        id="loginPassword"
        type="password"
        label="Password"
        variant="outlined"
        helperText="THERE ARE NO SECRETS FROM YOURSELF"
      />
      <Button variant="contained" onClick={handleSubmit}>
        SUBMIT OF YOUR OVERLORDS
      </Button>
      <Backdrop open={processing}>
        <CircularProgress />
      </Backdrop>
    </Box>
  );
}
export default LoginPage;
