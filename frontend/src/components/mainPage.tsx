import { lazy, Suspense, useEffect, useState } from "react";
import pageSelectorController from "../pageSelection/pageSelectorController";
import { Alert, CircularProgress, Container } from "@mui/material";
import pageSelectorModel from "../pageSelection/pageSelectorModel";
import PageSelection from "../pageSelection/pageEnum";
import DefaultPageSuspence from "../support/defaultPageSuspence";
const ItemPage = lazy(() => import("../listPages/itemPage/itemPage"));
const LoginPage = lazy(() => import("../login/loginPage"));
// const UsersPage = lazy(() => import("../listPages/usersPage/usersPage"));

function MainPage({}: any) {
  const [pageSelection, setPageSelection] = useState(
    pageSelectorModel.pageSelection
  );

  useEffect(() => {
    pageSelectorController.subscribeView("mainPage", setPageSelection);
    return () => {
      pageSelectorController.unsubscribeView("mainPage");
    };
  });

  let output: any;
  switch (pageSelection) {
    case PageSelection.Items:
      output = (
        <Suspense fallback={<CircularProgress sx={{ alignSelf: "center" }} />}>
          <ItemPage />
        </Suspense>
      );
      break;

    case PageSelection.Storages:
      output = (
        <Container>
          <Alert severity="info">Storages page W.I.P.</Alert>
        </Container>
      );
      break;

    case PageSelection.Users:
      output = (
        <Suspense fallback={<DefaultPageSuspence />}>
          <Alert severity="info">Users page W.I.P.</Alert>
        </Suspense>
      );
      break;

    case PageSelection.Login:
      output = (
        <Suspense fallback={<DefaultPageSuspence />}>
          <LoginPage />
        </Suspense>
      );
      break;

    default:
      output = (
        <Container>
          <Alert variant="filled" severity="error">
            Shit's on fire, yo!
          </Alert>
        </Container>
      );
  }

  return <>{output}</>;
}
export default MainPage;
