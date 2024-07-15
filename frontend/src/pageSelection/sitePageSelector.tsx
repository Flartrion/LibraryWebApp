import { Box, Container, Divider, Hidden, Tab } from "@mui/material";
import Tabs from "@mui/material/Tabs";
import { useEffect, useState } from "react";
import pageSelectorModel from "./pageSelectorModel";
import PageSelection from "./pageSelectionEnum";
import pageSelectorController from "./pageSelectorController";
import cookieWorker from "../support/cookieWorker";
import userDataModel from "../support/userDataModel";

function SitePageSelector() {
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
        <Tab label="Items" value={PageSelection.Items} tabIndex={PageSelection.Items} />
        <Tab label="Storages" value={PageSelection.Storages} tabIndex={PageSelection.Storages} />
        {userDataModel.userRole < 5 ? (
          <Tab label="Users" value={PageSelection.Users} tabIndex={PageSelection.Users} />
        ) : (
          ""
        )}
      </Tabs>
      <Divider />
    </Box>
  );
}
export default SitePageSelector;
