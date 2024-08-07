import { CircularProgress, Container } from "@mui/material";

function DefaultPageSuspence() {
  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        minHeight: "50%",
      }}
    >
      <CircularProgress size={"100px"} />
    </Container>
  );
}

export default DefaultPageSuspence;
