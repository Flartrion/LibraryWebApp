import { Box, Container, Divider, Tab } from "@mui/material";
import Tabs from "@mui/material/Tabs";
import { useEffect, useState } from "react";
import { pageSelectorController } from "./pageSelectorController";
import { pageSelectorModel } from "./pageSelectorModel";

export function SitePageSelector({ adminAccess }: any) {
  const [selection, setSelection] = useState(pageSelectorModel.pageSelection);
  function handleSelection(e: React.SyntheticEvent, newValue: number) {
    setSelection(newValue);
    pageSelectorController.updateModel(newValue);
  }
  useEffect(() => {
    pageSelectorController.subscribeView("pageSelector", setSelection);
    return () => {
      pageSelectorController.unsubscribeView("pageSelector");
    };
  });

  return (
    <Box>
      <Divider />
      <Container
        css={{
          borderBottom: "1px",
          borderColor: "black",
        }}
      >
        <Tabs value={selection} onChange={handleSelection} variant="fullWidth">
          <Tab label="Item 1" />
          <Tab label="Item 2" />
          <Tab label="Item 10" />
          <Tab label="Item 11" />
          <Tab label="Item 100" />
        </Tabs>
      </Container>
      <Divider />
    </Box>
  );
}
