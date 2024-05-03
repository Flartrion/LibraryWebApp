import { ThemeProvider } from "@mui/material";
import projectThemesArray from "../styles";
import { useEffect, useState } from "react";
import themeObserver from "./themeObserver";

function ThemeChangeObserver({ children }: any) {
  const [themeState, setTheme] = useState(0);
  useEffect(() => {
    themeObserver.updateView = (newTheme: number) => setTheme(newTheme);
  });

  return (
    <ThemeProvider theme={projectThemesArray[themeState][1]}>
      {children}
    </ThemeProvider>
  );
}
export default ThemeChangeObserver;
