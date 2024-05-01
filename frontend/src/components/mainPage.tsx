import { useEffect, useReducer, useState } from "react";
import { pageSelectorController } from "../controllers/pageSelectorController";
import { Alert, Box, Container } from "@mui/material";

export function MainPage({ someProp }: any) {
  const [pageSelection, setPageSelection] = useState(
    pageSelectorController.pageSelection
  );
  // Optimization? I don't know her.
  pageSelectorController.mainPageSetState = setPageSelection;

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
