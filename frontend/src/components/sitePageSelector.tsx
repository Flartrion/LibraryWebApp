import { Box, Container, Divider, Tab } from "@mui/material";
import Tabs from "@mui/material/Tabs";
import { useState } from "react";

export function SitePageSelector({ adminAccess }: any) {
  let [selection, setSelection] = useState(0);
  function handleSelection(e: React.SyntheticEvent, newValue: number) {
    setSelection(newValue);
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
          <Tab label="Item 1" index={0} />
          <Tab label="Item 2" index={1} />
          <Tab label="Item 3" index={2} />
          <Tab label="Item 4" index={3} />
          <Tab label="Item 10" index={4} />
        </Tabs>
      </Container>
      <Divider />
    </Box>
  );
}
