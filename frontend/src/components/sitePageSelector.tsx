import { Box, Container, Divider, Tab } from "@mui/material";
import Tabs from "@mui/material/Tabs";
import { useState } from "react";
import { pageSelectorController } from "../controllers/pageSelectorController";

export function SitePageSelector({ adminAccess }: any) {
  const [selection, setSelection] = useState(0);
  function handleSelection(e: React.SyntheticEvent, newValue: number) {
    setSelection(newValue);
    pageSelectorController.pageSelection = newValue;
    pageSelectorController.update(newValue);
  }


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
          <Tab label="Item 1"/>
          <Tab label="Item 2"/>
          <Tab label="Item 10"/>
          <Tab label="Item 11"/>
          <Tab label="Item 100"/>
        </Tabs>
      </Container>
      <Divider />
    </Box>
  );
}
