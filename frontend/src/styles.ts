import { Theme } from "@emotion/react";
import { createTheme } from "@mui/material/styles";

type NamedTheme = [string, Theme];

const projectThemesArray: NamedTheme[] = [
  [
    "Abominable Classique",
    createTheme({
      palette: {
        mode: "dark",
        primary: {
          main: "#ff0089",
        },
        secondary: {
          main: "#ff0000",
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
export default projectThemesArray;
