import { lazy, Suspense, useEffect, useState } from "react";
import pageSelectorController from "../pageSelection/pageSelectorController";
import { Alert, CircularProgress, Container } from "@mui/material";
import pageSelectorModel from "../pageSelection/pageSelectorModel";
import Page from "../pageSelection/pageSelectionEnum";
const ItemPage = lazy(() => import("../listPages/itemPage/itemPage"));
const LoginPage = lazy(() => import("../login/loginPage"));
const UsersPage = lazy(() => import("../listPages/usersPage/usersPage"));

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
    case Page.Items:
      // TODO: fow now just "true", fix after login implementation
      output = (
        <Suspense fallback={<CircularProgress sx={{ alignSelf: "center" }} />}>
          <ItemPage adminRights={true} />
        </Suspense>
      );
      break;

    case Page.Storages:
      output = (
        <Container>
          <Alert severity="info">Storages page W.I.P.</Alert>
        </Container>
      );
      break;

    case Page.Users:
      output = (
        <Suspense fallback={<CircularProgress sx={{ alignSelf: "center" }} />}>
          <UsersPage adminRights={true}></UsersPage>
        </Suspense>
      );
      break;

    case Page.Login:
      output = (
        <Suspense fallback={<CircularProgress sx={{ alignSelf: "center" }} />}>
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
