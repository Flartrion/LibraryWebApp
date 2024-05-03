import { Box, Divider, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import ItemPageTab from "./itemPageTabsEnum";
import itemPageController from "./itemPageController";
import itemPageModel from "./itemPageModel";
import BookItemList from "./itemList";

function ItemPage({ adminRights }: any) {
  const [tabSelection, setTabSelection] = useState(itemPageModel.tabSelection);

  useEffect(() => {
    itemPageController.subscribeView("itemPage", setTabSelection);
    return () => {
      itemPageController.unsubscribeView("itemPage");
    };
  });

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    itemPageController.updateModel(newValue);
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ItemPageTab.Items:
        return <BookItemList />;
      case ItemPageTab.Filters:
        return "No";
      case ItemPageTab.Item:
        return "very no";
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" key={ItemPageTab.Items} />
        <Tab label="Filters" key={ItemPageTab.Filters} />
        {/* Don't show if no item is selected */}
        {itemPageModel.itemSelection > -1 ? (
          <Tab label="Item" key={ItemPageTab.Item} />
        ) : (
          ""
        )}
        {/* Don't show if no admin privilegies. */}
        {/* TODO: Admin privilegies */}
        {adminRights ? <Tab label="Add Item" key={ItemPageTab.AddItem} /> : ""}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default ItemPage;
