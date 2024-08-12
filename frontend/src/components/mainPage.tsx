import { lazy, Suspense, useEffect, useState } from "react"
import pageSelectorController from "../pageSelection/pageSelectorController"
import { Alert, Box, Container } from "@mui/material"
import pageSelectorModel from "../pageSelection/pageSelectorModel"
import PageSelection from "../pageSelection/pageEnum"
import DefaultFallback from "../support/fallbacks/defaultFallback"
const UserPage = lazy(() => import("../listPages/usersPage/userPage"))
const ItemPage = lazy(() => import("../listPages/itemPage/itemPage"))
const StoragePage = lazy(() => import("../listPages/storagePage/storagePage"))
const LoginPage = lazy(() => import("../login/loginPage"))

function MainPage({}: any) {
  const [pageSelection, setPageSelection] = useState(
    pageSelectorModel.pageSelection
  )

  useEffect(() => {
    pageSelectorController.subscribeView("mainPage", setPageSelection)
    return () => {
      pageSelectorController.unsubscribeView("mainPage")
    }
  })

  let output: any
  switch (pageSelection) {
    case PageSelection.Items:
      output = (
        <Suspense fallback={<DefaultFallback />}>
          <ItemPage />
        </Suspense>
      )
      break

    case PageSelection.Storages:
      output = (
        <Suspense fallback={<DefaultFallback />}>
          <StoragePage />
        </Suspense>
      )
      break

    case PageSelection.Users:
      output = (
        <Suspense fallback={<DefaultFallback />}>
          <UserPage />
        </Suspense>
      )
      break

    case PageSelection.Login:
      output = (
        <Suspense fallback={<DefaultFallback />}>
          <LoginPage />
        </Suspense>
      )
      break

    default:
      output = (
        <Container>
          <Alert variant="filled" severity="error">
            Shit's on fire, yo!
          </Alert>
        </Container>
      )
  }

  // return <Box>{output}</Box>
  return output
}
export default MainPage
