import { Box, Container, TextField } from "@mui/material";

function LoginPage() {
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
    </Box>
  );
}
export default LoginPage;
