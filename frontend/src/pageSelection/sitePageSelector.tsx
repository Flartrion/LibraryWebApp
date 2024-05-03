import { Box, Container, Divider, Hidden, Tab } from "@mui/material";
import Tabs from "@mui/material/Tabs";
import { useEffect, useState } from "react";
import pageSelectorModel from "./pageSelectorModel";
import Page from "./pageSelectionEnum";
import pageSelectorController from "./pageSelectorController";

function SitePageSelector({ adminAccess }: any) {
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
      <Tabs value={selection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Items" key={Page.Items} />
        <Tab label="Storages" key={Page.Storages} />
        <Tab label="Users" key={Page.Users} />
        {selection == Page.Login ? <Tab label="Login" key={Page.Login} /> : ""}
      </Tabs>
      <Divider />
    </Box>
  );
}
export default SitePageSelector;
