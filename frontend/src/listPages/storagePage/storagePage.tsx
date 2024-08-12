import { Box, Divider, Tab, Tabs } from "@mui/material"
import { lazy, Suspense, useEffect, useState } from "react"
import storagePageController from "./storagePageController"
import storagePageModel from "./storagePageModel"
import userDataModel from "../../support/userDataModel"
import DefaultFallback from "../../support/fallbacks/defaultFallback"
import ListTab from "../support/listTab"
import storageViewModel from "./storageViewPage/storageViewModel"
import storageListController from "./storageListPage/storageListController"
import storageListModel from "./storageListPage/storageListModel"
import renderRow from "./storageListPage/renderRow"
import storageFilterModel from "./storageFilterPage/storageFilterModel"
import storageFilterReducer from "./storageFilterPage/storageFilterReducer"
import StorageTextFieldsAbstract from "./support/storageTextFieldsAbstract"
import storageAddController from "./storageAddPage/storageAddController"
import storageAddModel from "./storageAddPage/storageAddModel"
import storageAddReducer from "./storageAddPage/storageAddReducer"
import StorageActionEnum from "./support/storageActionEnum"

const GenericFilterPage = lazy(
  () => import("../../components/filterPage/genericFIlterPage")
)
const GenericVirtualList = lazy(
  () => import("../../components/listPage/genericList")
)
const GenericAddPage = lazy(
  () => import("../../components/addPage/genericAddPage")
)
const StorageViewPage = lazy(() => import("./storageViewPage/storageViewPage"))

function StoragePage() {
  const [tabSelection, setTabSelection] = useState(
    storagePageModel.tabSelection
  )
  const [viewedItem, setViewedItem] = useState(storageViewModel.item)

  useEffect(() => {
    storageListController.setViewedState = setViewedItem
    storagePageController.subscribeView("itemPage", setTabSelection)
    return () => {
      storageListController.setViewedState = undefined
      storagePageController.unsubscribeView("itemPage")
    }
  })

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    storagePageController.updateModel(newValue)
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ListTab.Items:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericVirtualList
              listController={storageListController}
              listModel={storageListModel}
              renderRow={renderRow}
            />
          </Suspense>
        )
      case ListTab.Filters:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericFilterPage
              attachedModel={storageFilterModel}
              listController={storageListController}
              reducer={storageFilterReducer}
              textFieldGroup={StorageTextFieldsAbstract}
            />
          </Suspense>
        )
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <StorageViewPage storage={viewedItem} />
          </Suspense>
        )
      case ListTab.Add:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericAddPage
              addController={storageAddController}
              attachedModel={storageAddModel}
              reducer={storageAddReducer}
              textFieldGroup={StorageTextFieldsAbstract}
              enumUsed={StorageActionEnum}
            />
          </Suspense>
        )
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" value={ListTab.Items} />
        <Tab label="Filters" value={ListTab.Filters} />
        {storageViewModel.item != undefined && (
          <Tab label="Storage" value={ListTab.View} />
        )}
        {(userDataModel.userRole ?? 999) <= 5 && (
          <Tab label="Add Storage" value={ListTab.Add} />
        )}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  )
}

export default StoragePage
