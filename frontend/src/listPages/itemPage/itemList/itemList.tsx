import { CircularProgress, ListItemButton, ListItemText } from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { areEqual, FixedSizeList } from "react-window";
import { memo, useEffect, useMemo, useState } from "react";
import itemListController from "./itemListController";
import itemListModel from "./itemListModel";
import Item from "../../../dataclasses/item";

const renderRow = memo(({ data, index, style }: any) => {
  const [itemSelection, items] = data;
  const item: Item = items[index];
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
        itemListController.changeSelection(index);
      }}
      selected={itemSelection === index}
    >
      {/* <ListItemIcon>
        <Check />
      </ListItemIcon> */}
      <ListItemText primary={item.title} secondary={item.authors} />
    </ListItemButton>
  );
}, areEqual);

// TODO: Gotta replace this with virtualized grid or something eventually if I want to implement pictures or something like that.
// Do I, though?
function BookItemList() {
  const [selectedIndex, setItemSelection] = useState(
    itemListModel.itemSelection
  );
  const [itemsLoaded, setLoaded] = useState(itemListModel.itemsLoaded);
  const data: [number, Item[]] = useMemo(
    () => [selectedIndex, itemListModel.items],
    [selectedIndex, itemListModel.items]
  );

  useEffect(() => {
    itemListController.setItemSelection = setItemSelection;
    itemListController.setItemsLoaded = setLoaded;
    if (!itemListModel.itemsLoaded) {
      itemListController.getInitial();
      //TODO: Get the controller to actually load stuff
    }
    return () => {
      itemListController.setItemSelection = undefined;
      itemListController.setItemsLoaded = undefined;
    };
  });

  return (
    <ReactVirtualizedAutoSizer>
      {({ height, width }: any) =>
        itemsLoaded ? (
          <FixedSizeList
            className="List"
            height={height}
            width={width}
            itemData={data}
            itemCount={itemListModel.items.length}
            itemSize={50}
            itemKey={(index, data) => {
              return data[1][index].id;
            }}
            onScroll={(props) => {
              itemListModel.scrollOffset = props.scrollOffset;
            }}
            initialScrollOffset={itemListModel.scrollOffset}
          >
            {renderRow}
          </FixedSizeList>
        ) : (
          <CircularProgress size={"100px"} />
        )
      }
    </ReactVirtualizedAutoSizer>
  );
}
export default BookItemList;
