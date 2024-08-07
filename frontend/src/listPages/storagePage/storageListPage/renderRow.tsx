import { memo } from "react";
import { areEqual } from "react-window";
import { ListItemButton, ListItemText } from "@mui/material";
import storageListController from "./storageListController";
import Storage from "../../../dataclasses/storage";

const renderRow = memo(({ data, index, style }: any) => {
  const [storageSelection, storages] = data;
  const storage: Storage = storages[index];
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
        storageListController.changeSelection(index);
      }}
      selected={storageSelection === index}
    >
      {/* <ListItemIcon>
          <Check />
        </ListItemIcon> */}
      <ListItemText primary={storage.address} secondary={"ID: " + storage.id} />
    </ListItemButton>
  );
}, areEqual);

export default renderRow;
