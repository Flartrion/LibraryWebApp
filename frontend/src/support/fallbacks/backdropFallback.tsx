import { Backdrop, CircularProgress, Container } from "@mui/material";

function BackdropFallback() {
  return (
    <Backdrop open={true}>
      <CircularProgress size={"100px"} />
    </Backdrop>
  );
}

export default BackdropFallback;
