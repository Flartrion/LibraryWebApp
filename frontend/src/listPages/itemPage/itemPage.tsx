import { Box, Divider, List, Tab, Tabs } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import itemPageController from "./itemPageController";
import itemPageModel from "./itemPageModel";
import BookItemList from "./itemList/itemList";
import userDataModel from "../../support/userDataModel";
import itemViewModel from "./itemViewPage/itemViewModel";
import DefaultPageSuspence from "../../support/defaultPageSuspence";
import itemListController from "./itemList/itemListController";
import ItemFilterPage from "./itemFilterPage/itemFIlterPage";
import ListTab from "../support/listTab";
import itemAddController from "./itemAddPage/itemAddController";
import itemAddModel from "./itemAddPage/itemAddModel";
import reducer from "./itemAddPage/reducer";
import ItemTextFieldsAbstract from "./support/itemTextFieldsAbstract";
const GeneralAddPage = lazy(
  () => import("../../components/addPage/generalAddPage")
);
const ItemViewPage = lazy(() => import("./itemViewPage/ItemViewPage"));

function ItemPage() {
  const [tabSelection, setTabSelection] = useState(itemPageModel.tabSelection);
  const [viewedItem, setViewedItem] = useState(undefined);

  useEffect(() => {
    itemListController.setViewedItem = setViewedItem;
    itemPageController.subscribeView("itemPage", setTabSelection);
    return () => {
      itemListController.setViewedItem = undefined;
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
      case ListTab.Items:
        return <BookItemList />;
      case ListTab.Filters:
        return <ItemFilterPage />;
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultPageSuspence />}>
            <ItemViewPage item={itemViewModel.item}></ItemViewPage>;
          </Suspense>
        );
      case ListTab.Add:
        return (
          <Suspense fallback={<DefaultPageSuspence />}>
            <GeneralAddPage
              addController={itemAddController}
              attachedModel={itemAddModel}
              reducer={reducer}
              textFieldGroup={ItemTextFieldsAbstract}
            ></GeneralAddPage>
          </Suspense>
        );
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" key={ListTab.Items} tabIndex={ListTab.Items} />
        <Tab label="Filters" key={ListTab.Filters} tabIndex={ListTab.Filters} />
        {/* BUG: Apparently not displaying one Tab shifts indexes (God, why?) of all tabs after that,
         so I can not just hide it, I have to go out of my way to disable it. Why can't the numbering 
         just remain consistent?*/}
        <Tab
          disabled={itemViewModel.item == undefined}
          label="Item"
          key={ListTab.View}
          tabIndex={ListTab.View}
        />
        <Tab
          disabled={(userDataModel.userRole ?? 999) > 5}
          label="Add Item"
          key={ListTab.Add}
          tabIndex={ListTab.Add}
        />
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default ItemPage;
