import { Box, CircularProgress } from "@mui/material"

function DefaultFallback() {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        flex: "1 1 auto",
      }}
    >
      <CircularProgress size={"100px"} />
    </Box>
  )
}

export default DefaultFallback
