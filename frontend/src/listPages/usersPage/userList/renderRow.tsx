import { memo } from "react";
import { areEqual } from "react-window";
import { ListItemButton, ListItemText } from "@mui/material";
import User from "../../../dataclasses/user";
import userListController from "./userListController";

const renderRow = memo(({ data, index, style }: any) => {
  const [userSelection, users] = data;
  const user: User = users[index];
  // console.log(
  //   "I'm item " +
  //     index +
  //     "! I've been re-rendered at " +
  //     new Date().toLocaleTimeString() +
  //     "!"
  // );
  return (
    <ListItemButton
      style={style}
      key={index}
      divider={true}
      onClick={(event) => {
        event.stopPropagation();
        userListController.changeSelection(index);
      }}
      selected={userSelection === index}
    >
      {/* <ListItemIcon>
          <Check />
        </ListItemIcon> */}
      <ListItemText
        primary={user.fullName}
        secondary={"Privilege level: " + user.role}
      />
    </ListItemButton>
  );
}, areEqual);

export default renderRow;
