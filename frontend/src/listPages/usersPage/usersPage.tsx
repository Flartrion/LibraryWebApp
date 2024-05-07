import { Box, Divider, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import UsersPageTab from "./usersPageTabsEnum";
import usersPageController from "./usersPageController";
import usersPageModel from "./usersPageModel";
import UserList from "./userList";

function UsersPage({ adminRights }: any) {
  const [tabSelection, setTabSelection] = useState(usersPageModel.tabSelection);

  useEffect(() => {
    usersPageController.subscribeView("usersPage", setTabSelection);
    return () => {
      usersPageController.unsubscribeView("usersPage");
    };
  });

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    usersPageController.updateModel(newValue);
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case UsersPageTab.UsersList:
        return <UserList />;
      case UsersPageTab.Filters:
        return "No";
      case UsersPageTab.User:
        return "very no";
      case UsersPageTab.AddUser:
        return "very-very no";
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" tabIndex={UsersPageTab.UsersList} />
        <Tab label="Filters" tabIndex={UsersPageTab.Filters} />
        {/* BUG: Apparently not displaying one Tab shifts indexes (God, why?) of all tabs after that,
         so I can not just hide it, I have to go out of my way to disable it. Why can't the numbering 
         just remain consistent?*/}
        <Tab
          disabled={usersPageModel.itemSelection < 0}
          label="Item"
          tabIndex={UsersPageTab.User}
        />
        // TODO: Replace with a floating action? Will make this entire design more tolerable for mobile, too
        <Tab label="Add Item" tabIndex={UsersPageTab.AddUser} /> 
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default UsersPage;
