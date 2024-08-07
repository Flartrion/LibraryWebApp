import { Box, Divider, Tab, Tabs } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import userPageController from "./userPageController";
import userPageModel from "./userPageModel";
import userDataModel from "../../support/userDataModel";
import userViewModel from "./userViewPage/userViewModel";
import DefaultPageSuspence from "../../support/defaultPageSuspence";
import userListController from "./userList/userListController";
import ListTab from "../support/listTab";
import GenericVirtualList from "../../components/listPage/genericList";
import userListModel from "./userList/userListModel";
import renderRow from "./userList/renderRow";
import GenericFilterPage from "../../components/filterPage/genericFIlterPage";
import userFilterModel from "./userFilterPage/userFilterModel";
import filterReducer from "./userFilterPage/reducer";
import UserTextFieldsAbstract from "./support/userTextFieldsAbstract";
import userAddController from "./userAddPage/userAddController";
import userAddModel from "./userAddPage/userAddModel";
import addReducer from "./userAddPage/reducer";
import UserActionEnum from "./support/userActionEnum";
const GenericAddPage = lazy(
  () => import("../../components/addPage/genericAddPage")
);
const UserViewPage = lazy(() => import("./userViewPage/userViewPage"));

function UsersPage() {
  const [tabSelection, setTabSelection] = useState(userPageModel.tabSelection);
  const [viewedItem, setViewedItem] = useState(userViewModel.item);

  useEffect(() => {
    userListController.setViewedState = setViewedItem;
    userPageController.subscribeView("itemPage", setTabSelection);
    return () => {
      userListController.setViewedState = undefined;
      userPageController.unsubscribeView("itemPage");
    };
  });

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    userPageController.updateModel(newValue);
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ListTab.Items:
        return (
          <GenericVirtualList
            listController={userListController}
            listModel={userListModel}
            renderRow={renderRow}
          />
        );
      case ListTab.Filters:
        return (
          <GenericFilterPage
            attachedModel={userFilterModel}
            listController={userListController}
            reducer={filterReducer}
            textFieldGroup={UserTextFieldsAbstract}
          />
        );
      case ListTab.View:
        return (
          <Suspense fallback={<DefaultPageSuspence />}>
            <UserViewPage user={viewedItem} />
          </Suspense>
        );
      case ListTab.Add:
        return (
          <Suspense fallback={<DefaultPageSuspence />}>
            <GenericAddPage
              addController={userAddController}
              attachedModel={userAddModel}
              reducer={addReducer}
              textFieldGroup={UserTextFieldsAbstract}
              enumUsed={UserActionEnum}
            />
          </Suspense>
        );
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" tabIndex={ListTab.Items} />
        <Tab label="Filters" tabIndex={ListTab.Filters} />
        {/* BUG: Apparently not displaying one Tab shifts indexes (God, why?) of all tabs after that,
         so I can not just hide it, I have to go out of my way to disable it. Why can't the numbering 
         just remain consistent?*/}
        <Tab
          disabled={userViewModel.item == undefined}
          label="User"
          tabIndex={ListTab.View}
        />
        <Tab
          disabled={(userDataModel.userRole ?? 999) > 5}
          label="Add User"
          tabIndex={ListTab.Add}
        />
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default UsersPage;
