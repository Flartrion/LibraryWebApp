import { Box, CircularProgress, Divider, Tab, Tabs } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import ItemPageTab from "./itemPageTabsEnum";
import itemPageController from "./itemPageController";
import itemPageModel from "./itemPageModel";
import BookItemList from "./itemList/itemList";
import itemListModel from "./itemList/itemListModel";
import userDataModel from "../../support/userDataModel";
import ItemViewPage from "./itemViewPage/ItemViewPage";
import itemViewModel from "./itemViewPage/itemViewModel";
const ItemAddPage = lazy(() => import("./itemAddPage/itemAddPage"));

function ItemPage() {
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
        return <ItemViewPage item={itemViewModel.item}></ItemViewPage>;
      case ItemPageTab.AddItem:
        return (
          <Suspense fallback={<CircularProgress size="100px" />}>
            <ItemAddPage />
          </Suspense>
        );
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" tabIndex={ItemPageTab.Items} />
        <Tab label="Filters" tabIndex={ItemPageTab.Filters} />
        {/* BUG: Apparently not displaying one Tab shifts indexes (God, why?) of all tabs after that,
         so I can not just hide it, I have to go out of my way to disable it. Why can't the numbering 
         just remain consistent?*/}
        <Tab
          disabled={itemListModel.itemSelection < 0}
          label="Item"
          tabIndex={ItemPageTab.Item}
        />
        <Tab
          disabled={(userDataModel.userRole ?? 999) > 5}
          label="Add Item"
          tabIndex={ItemPageTab.AddItem}
        />
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default ItemPage;
