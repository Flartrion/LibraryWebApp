import { Box, CssBaseline, ThemeProvider } from "@mui/material";
import { createRoot } from "react-dom/client";
import { themes } from "./styles";
import { SiteHeader } from "./components/header";
import { SitePageSelector } from "./components/sitePageSelector";
import { MainPage } from "./components/mainPage";

const container = document.getElementById("root");
const root = createRoot(container!);
root.render(
  <ThemeProvider theme={themes.abominable}>
    <CssBaseline />
    <Box
      css={{
        padding: "0%",
        margin: "0%",
        display: "flex",
        flexDirection: "column",
        flexBasis: "flexGrow",
        WebkitJustifyContent: "space-between",
        alignContent: "space-between",
        width: "100%",
        length: "max-content",
      }}
    >
      <SiteHeader siteName={document.title} />
      <SitePageSelector />
      <MainPage />
    </Box>
  </ThemeProvider>
);
