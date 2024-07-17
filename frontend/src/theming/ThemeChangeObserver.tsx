import { ThemeProvider } from "@mui/material";
import { useEffect, useState } from "react";
import themeObserver from "./themeObserver";
import userDataModel from "../support/userDataModel";
import projectThemesArray from "../styles";

function ThemeChangeObserver({ children }: any) {
  const [themeState, setTheme] = useState(userDataModel.userTheme);
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
