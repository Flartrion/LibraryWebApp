import { CssBaseline } from "@mui/material"
import { createRoot } from "react-dom/client"
import MainPage from "./components/mainPage"
import ThemeChangeObserver from "./theming/ThemeChangeObserver"
import { lazy, Suspense } from "react"
import DefaultFallback from "./support/fallbacks/defaultFallback"
const SiteHeader = lazy(() => import("./header/siteHeader"))
const SitePageSelector = lazy(() => import("./pageSelection/sitePageSelector"))

const container = document.getElementById("root")
const root = createRoot(container!)
root.render(
  <ThemeChangeObserver>
    <CssBaseline />
    <Suspense fallback={<DefaultFallback />}>
      <SiteHeader siteName={document.title} />
      <SitePageSelector />
    </Suspense>
    <MainPage />
  </ThemeChangeObserver>
)
