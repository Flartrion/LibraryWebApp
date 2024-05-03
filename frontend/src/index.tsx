import { CssBaseline } from "@mui/material";
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
    <SiteHeader siteName={document.title} />
    <SitePageSelector />
    <MainPage />
  </ThemeChangeObserver>
);
