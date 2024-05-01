import { createTheme } from "@mui/material/styles";

export const themes = [
  [
    "Abominable Classique",
    createTheme({
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
    }),
  ],
  [
    "Pure, proper and powerful",
    createTheme({
      palette: {
        mode: "dark",
        primary: {
          main: "#1976d2",
        },
        secondary: {
          main: "#f50057",
        },
      },
      shape: {
        borderRadius: 20,
      },
    }),
  ],
];
