import { ThemeProvider } from "@mui/material";
import { themes } from "../styles";
import { useState } from "react";
import { themeObserver } from "../controllers/themeObserver";

export function ThemeChangeObserver({ children }: any) {
  const [themeState, setTheme] = useState(0);
  themeObserver.updateView = (newTheme: number) => setTheme(newTheme);
  return (
    <ThemeProvider theme={(themes[themeState][1])}>
      {children}
    </ThemeProvider>
  );
}
