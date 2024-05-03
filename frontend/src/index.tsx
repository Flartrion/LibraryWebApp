import { Box, CssBaseline } from "@mui/material";
import { createRoot } from "react-dom/client";
import SiteHeader from "./components/siteHeader";
import SitePageSelector from "./pageSelection/sitePageSelector";
import MainPage from "./components/mainPage";
import ThemeChangeObserver from "./theming/ThemeChangeObserver";

const container = document.getElementById("root");
const root = createRoot(container!);
root.render(
  <ThemeChangeObserver>
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
  </ThemeChangeObserver>
);
