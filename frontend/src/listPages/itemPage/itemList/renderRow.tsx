import { memo } from "react"
import Item from "../../../dataclasses/item"
import { ListItemButton, ListItemText } from "@mui/material"
import itemListController from "./itemListController"
import { areEqual } from "react-window"

const renderRow = memo(({ data, index, style }: any) => {
  const [itemSelection, items] = data
  const item: Item = items[index]
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
        event.stopPropagation()
        itemListController.changeSelection(index)
      }}
      selected={itemSelection === index}
    >
      {/* <ListItemIcon>
          <Check />
        </ListItemIcon> */}
      <ListItemText primary={item.title} secondary={item.authors} />
    </ListItemButton>
  )
}, areEqual)

export default renderRow
