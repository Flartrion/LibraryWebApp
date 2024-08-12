import { Box, Divider, Tab, Tabs } from "@mui/material"
import { lazy, Suspense, useEffect, useState } from "react"
import itemPageController from "./itemPageController"
import itemPageModel from "./itemPageModel"
import userDataModel from "../../support/userDataModel"
import itemViewModel from "./itemViewPage/itemViewModel"
import DefaultFallback from "../../support/fallbacks/defaultFallback"
import itemListController from "./itemList/itemListController"
import ListTab from "../support/listTab"
import itemAddController from "./itemAddPage/itemAddController"
import itemAddModel from "./itemAddPage/itemAddModel"
import addReducer from "./itemAddPage/reducer"
import ItemTextFieldsAbstract from "./support/itemTextFieldsAbstract"
import itemFilterModel from "./itemFilterPage/itemFilterModel"
import filterReducer from "./itemFilterPage/reducer"
import renderRow from "./itemList/renderRow"
import itemListModel from "./itemList/itemListModel"
import Item from "../../dataclasses/item"
import ItemActionEnum from "./support/itemActionEnum"
const GenericVirtualList = lazy(
  () => import("../../components/listPage/genericList")
)
const GeneralAddPage = lazy(
  () => import("../../components/addPage/genericAddPage")
)
const GenericFilterPage = lazy(
  () => import("../../components/filterPage/genericFIlterPage")
)
const ItemViewPage = lazy(() => import("./itemViewPage/ItemViewPage"))

function ItemPage() {
  const [tabSelection, setTabSelection] = useState(itemPageModel.tabSelection)
  const [viewedItem, setViewedState] = useState<Item>(itemViewModel.item)

  useEffect(() => {
    itemListController.setViewedState = setViewedState
    itemPageController.subscribeView("itemPage", setTabSelection)
    return () => {
      itemListController.setViewedState = undefined
      itemPageController.unsubscribeView("itemPage")
    }
  }, [itemListController, itemPageController])

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    itemPageController.updateModel(newValue)
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
        )
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
        )
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <ItemViewPage item={viewedItem}></ItemViewPage>
          </Suspense>
        )
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
        )
    }
  }

  return (
    <Box
      display="flex"
      flexBasis={"auto"}
      flexGrow={1}
      flexShrink={0}
      flexDirection="column"
    >
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" key={ListTab.Items} value={ListTab.Items} />
        <Tab label="Filters" key={ListTab.Filters} value={ListTab.Filters} />
        {itemViewModel.item != undefined && (
          <Tab label="Item" key={ListTab.View} value={ListTab.View} />
        )}
        {(userDataModel.userRole ?? 999) <= 5 && (
          <Tab label="Add Item" key={ListTab.Add} value={ListTab.Add} />
        )}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  )
}

export default ItemPage
