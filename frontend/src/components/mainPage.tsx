import { useEffect, useReducer, useState } from "react";
import { pageSelectorController } from "../pageSelection/pageSelectorController";
import { Alert, Box, Container } from "@mui/material";
import { pageSelectorModel } from "../pageSelection/pageSelectorModel";

export function MainPage({ someProp }: any) {
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
    case 0:
      output = pageSelection.toString(2);
      break;

    case 1:
      output = pageSelection.toString(2);
      break;

    case 2:
      output = pageSelection.toString(2);
      break;

    case 3:
      output = pageSelection.toString(2);
      break;

    case 4:
      output = pageSelection.toString(2);
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

  return <Box>{output}</Box>;
}
