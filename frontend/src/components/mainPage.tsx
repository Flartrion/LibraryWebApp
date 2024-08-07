import { lazy, Suspense, useEffect, useState } from "react";
import pageSelectorController from "../pageSelection/pageSelectorController";
import { Alert, Container } from "@mui/material";
import pageSelectorModel from "../pageSelection/pageSelectorModel";
import PageSelection from "../pageSelection/pageEnum";
import DefaultPageSuspence from "../support/defaultPageSuspence";
const UserPage = lazy(() => import("../listPages/usersPage/userPage"));
const ItemPage = lazy(() => import("../listPages/itemPage/itemPage"));
const StoragePage = lazy(() => import("../listPages/storagePage/storagePage"));
const LoginPage = lazy(() => import("../login/loginPage"));

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
        <Suspense fallback={<DefaultPageSuspence />}>
          <ItemPage />
        </Suspense>
      );
      break;

    case PageSelection.Storages:
      output = (
        <Suspense fallback={<DefaultPageSuspence />}>
          <StoragePage />
        </Suspense>
      );
      break;

    case PageSelection.Users:
      output = (
        <Suspense fallback={<DefaultPageSuspence />}>
          <UserPage />
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
