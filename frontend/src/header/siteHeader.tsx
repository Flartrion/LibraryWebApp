import { Box, CircularProgress } from "@mui/material"
import { lazy, Suspense } from "react"
const HeaderMenu = lazy(() => import("./dropoutMenu"))

function SiteHeader({ siteName }: any) {
  return (
    <Box
      css={{
        paddingLeft: "50px",
        paddingRight: "30px",
        alignItems: "center",
        minHeight: "50px",
        maxHeight: "20vh",
      }}
      sx={{
        display: "flex",
        flex: "0 1 content",
        justifyContent: "space-between",
      }}
    >
      <h1>{siteName}</h1>
      <Suspense fallback={<CircularProgress />}>
        <HeaderMenu />
      </Suspense>
    </Box>
  )
}

export default SiteHeader
