import { ThemeProvider } from "@mui/material";
import projectThemesArray from "../styles";
import { useEffect, useState } from "react";
import themeObserver from "./themeObserver";
import cookieWorker from "../support/cookieWorker";

function ThemeChangeObserver({ children }: any) {
  const [themeState, setTheme] = useState(
    Number(cookieWorker.extractCookie("userTheme") ?? 0)
  );
  useEffect(() => {
    themeObserver.currentTheme = themeState;
    themeObserver.updateView = (newTheme: number) => setTheme(newTheme);
  });

  return (
    <ThemeProvider theme={projectThemesArray[themeState][1]}>
      {children}
    </ThemeProvider>
  );
}
export default ThemeChangeObserver;
