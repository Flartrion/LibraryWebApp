import { Box, Divider, Tab, Tabs } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import itemPageController from "./itemPageController";
import itemPageModel from "./itemPageModel";
import userDataModel from "../../support/userDataModel";
import itemViewModel from "./itemViewPage/itemViewModel";
import DefaultFallback from "../../support/fallbacks/defaultFallback";
import itemListController from "./itemList/itemListController";
import ListTab from "../support/listTab";
import itemAddController from "./itemAddPage/itemAddController";
import itemAddModel from "./itemAddPage/itemAddModel";
import addReducer from "./itemAddPage/reducer";
import ItemTextFieldsAbstract from "./support/itemTextFieldsAbstract";
import itemFilterModel from "./itemFilterPage/itemFilterModel";
import filterReducer from "./itemFilterPage/reducer";
import renderRow from "./itemList/renderRow";
import itemListModel from "./itemList/itemListModel";
import Item from "../../dataclasses/item";
import ItemActionEnum from "./support/itemActionEnum";
const GenericVirtualList = lazy(
  () => import("../../components/listPage/genericList")
);
const GeneralAddPage = lazy(
  () => import("../../components/addPage/genericAddPage")
);
const GenericFilterPage = lazy(
  () => import("../../components/filterPage/genericFIlterPage")
);
const ItemViewPage = lazy(() => import("./itemViewPage/ItemViewPage"));

function ItemPage() {
  const [tabSelection, setTabSelection] = useState(itemPageModel.tabSelection);
  const [viewedItem, setViewedState] = useState<Item>(itemViewModel.item);

  useEffect(() => {
    itemListController.setViewedState = setViewedState;
    itemPageController.subscribeView("itemPage", setTabSelection);
    return () => {
      itemListController.setViewedState = undefined;
      itemPageController.unsubscribeView("itemPage");
    };
  }, [itemListController, itemPageController]);

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    itemPageController.updateModel(newValue);
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ListTab.Items:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericVirtualList
              renderRow={renderRow}
              listController={itemListController}
              listModel={itemListModel}
            />
          </Suspense>
        );
      case ListTab.Filters:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericFilterPage
              attachedModel={itemFilterModel}
              listController={itemListController}
              reducer={filterReducer}
              textFieldGroup={ItemTextFieldsAbstract}
            />
          </Suspense>
        );
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <ItemViewPage item={viewedItem}></ItemViewPage>;
          </Suspense>
        );
      case ListTab.Add:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GeneralAddPage
              addController={itemAddController}
              attachedModel={itemAddModel}
              reducer={addReducer}
              textFieldGroup={ItemTextFieldsAbstract}
              enumUsed={ItemActionEnum}
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
