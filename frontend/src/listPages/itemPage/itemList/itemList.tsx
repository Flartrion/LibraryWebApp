import { Check } from "@mui/icons-material";
import { ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { areEqual, FixedSizeList } from "react-window";
import React, { memo, useEffect } from "react";
import itemListController from "./itemListController";
import itemListModel from "./itemListModel";
import { log } from "console";

const renderRow = memo(({ data, index, style }: any) => {
  // function renderRow(props: ListChildComponentProps) {
  // const { index, style } = props;
  const itemSelection = data;
  return (
    <ListItemButton
      style={style}
      key={index}
      divider={true}
      onClick={(event) => {
        itemListController.updateModel(index);
      }}
      selected={itemSelection === index}
    >
      <ListItemIcon>
        <Check />
      </ListItemIcon>
      <ListItemText
        primary={"Name " + index.toString()}
        secondary={"Author " + index.toString()}
      />
    </ListItemButton>
  );
}, areEqual);

// TODO: Gotta replace this with virtualized grid or something eventually if I want to implement pictures or something like that.
// Do I, though?
function BookItemList() {
  const [selectedIndex, setItemSelection] = React.useState(
    itemListModel.itemSelection
  );

  useEffect(() => {
    itemListController.subscribeView("itemList", setItemSelection);
    return () => {
      itemListController.unsubscribeView("itemList");
    };
  });

  return (
    <ReactVirtualizedAutoSizer>
      {({ height, width }: any) => (
        <FixedSizeList
          className="List"
          height={height}
          width={width}
          itemData={selectedIndex}
          itemCount={100}
          itemSize={50}
          onScroll={(props) => {
            itemListModel.scrollOffset = props.scrollOffset;
          }}
          initialScrollOffset={itemListModel.scrollOffset}
        >
          {renderRow}
        </FixedSizeList>
      )}
    </ReactVirtualizedAutoSizer>
  );
}
export default BookItemList;
