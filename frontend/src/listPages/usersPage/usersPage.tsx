import { Box, Divider, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import UsersPageTab from "./usersPageTabsEnum";
import usersPageController from "./usersPageController";
import usersPageModel from "./usersPageModel";
import UsersList from "./usersList";

function UsersPage() {
  const [tabSelection, setTabSelection] = useState(usersPageModel.tabSelection);
  const [usersLoaded, setLoaded] = useState(usersPageModel.usersLoaded);

  useEffect(() => {
    usersPageController.subscribeView("usersPage", setTabSelection);
    usersPageController.setLoaded = setLoaded;
    return () => {
      usersPageController.setLoaded = null;
      usersPageController.unsubscribeView("usersPage");
    };
  });

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    usersPageController.updateModel(newValue);
  }

  function updateList(e: React.MouseEvent) {
    if (tabSelection == UsersPageTab.UsersList) {
      usersPageController.fetchUsers();
      // TODO: connect to controller
    }
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case UsersPageTab.UsersList:
        return <UsersList usersLoaded={usersLoaded} />;
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
        <Tab
          label="Results"
          tabIndex={UsersPageTab.UsersList}
          onClick={updateList}
        />
        <Tab label="Filters" tabIndex={UsersPageTab.Filters} />
        {/* BUG: Apparently not displaying one Tab shifts indexes (God, why?) of all tabs after that,
         so I can not just hide it, I have to go out of my way to disable it. Why can't the numbering 
         just remain consistent?*/}
        <Tab
          disabled={usersPageModel.userSelection < 0}
          label="Item"
          tabIndex={UsersPageTab.User}
        />
        // TODO: Replace with a floating action? Will make this entire design
        more tolerable for mobile, too
        <Tab label="Add Item" tabIndex={UsersPageTab.AddUser} />
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default UsersPage;
