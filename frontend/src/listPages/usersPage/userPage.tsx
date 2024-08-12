import { Box, Divider, Tab, Tabs } from "@mui/material"
import { lazy, Suspense, useEffect, useState } from "react"
import userPageController from "./userPageController"
import userPageModel from "./userPageModel"
import userDataModel from "../../support/userDataModel"
import userViewModel from "./userViewPage/userViewModel"
import DefaultFallback from "../../support/fallbacks/defaultFallback"
import userListController from "./userList/userListController"
import ListTab from "../support/listTab"
import userListModel from "./userList/userListModel"
import renderRow from "./userList/renderRow"
import userFilterModel from "./userFilterPage/userFilterModel"
import filterReducer from "./userFilterPage/reducer"
import UserTextFieldsAbstract from "./support/userTextFieldsAbstract"
import userAddController from "./userAddPage/userAddController"
import userAddModel from "./userAddPage/userAddModel"
import addReducer from "./userAddPage/reducer"
import UserActionEnum from "./support/userActionEnum"
const GenericFilterPage = lazy(
  () => import("../../components/filterPage/genericFIlterPage")
)
const GenericVirtualList = lazy(
  () => import("../../components/listPage/genericList")
)
const GenericAddPage = lazy(
  () => import("../../components/addPage/genericAddPage")
)
const UserViewPage = lazy(() => import("./userViewPage/userViewPage"))

function UsersPage() {
  const [tabSelection, setTabSelection] = useState(userPageModel.tabSelection)
  const [viewedItem, setViewedItem] = useState(userViewModel.item)

  useEffect(() => {
    userListController.setViewedState = setViewedItem
    userPageController.subscribeView("itemPage", setTabSelection)
    return () => {
      userListController.setViewedState = undefined
      userPageController.unsubscribeView("itemPage")
    }
  })

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    userPageController.updateModel(newValue)
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ListTab.Items:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericVirtualList
              listController={userListController}
              listModel={userListModel}
              renderRow={renderRow}
            />
          </Suspense>
        )
      case ListTab.Filters:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericFilterPage
              attachedModel={userFilterModel}
              listController={userListController}
              reducer={filterReducer}
              textFieldGroup={UserTextFieldsAbstract}
            />
          </Suspense>
        )
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <UserViewPage user={viewedItem} />
          </Suspense>
        )
      case ListTab.Add:
        return (
          <Suspense fallback={<DefaultFallback />}>
            <GenericAddPage
              addController={userAddController}
              attachedModel={userAddModel}
              reducer={addReducer}
              textFieldGroup={UserTextFieldsAbstract}
              enumUsed={UserActionEnum}
            />
          </Suspense>
        )
    }
  }

  return (
    <Box
      display="flex"
      flexBasis={"auto"}
      minHeight={0}
      maxHeight={"fill-available"}
      flexGrow={1}
      flexShrink={0}
      flexDirection="column"
    >
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" value={ListTab.Items} />
        <Tab label="Filters" value={ListTab.Filters} />
        {userViewModel.item != undefined && (
          <Tab label="User" value={ListTab.View} />
        )}
        {(userDataModel.userRole ?? 999) <= 5 && (
          <Tab label="Add User" value={ListTab.Add} />
        )}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  )
}

export default UsersPage
