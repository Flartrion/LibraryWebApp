import { createTheme } from "@mui/material/styles";

export const darkTheme = createTheme({
  palette: {
    mode: "dark",
    primary: {
      main: "#1976d2",
    },
    secondary: {
      main: "#9c27b0",
      contrastText: "#ffcdd2",
    },
    background: {
      default: "#250000",
      paper: "#270101",
    },
  },
  shape: {
    borderRadius: 20,
  },
});
