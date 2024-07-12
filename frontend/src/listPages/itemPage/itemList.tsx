import { Check } from "@mui/icons-material";
import { ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { areEqual, FixedSizeList } from "react-window";
import itemPageModel from "./itemPageModel";
import React, { memo } from "react";

const renderRow = memo(({ data, index, style }: any) => {
  // function renderRow(props: ListChildComponentProps) {
  // const { index, style } = props;
  const [selectedIndex, setSelectionIndex] = data;
  return (
    <ListItemButton
      style={style}
      key={index}
      divider={true}
      onClick={(event) => {
        setSelectionIndex(index);
      }}
      selected={selectedIndex == index}
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
  const [selectedIndex, setSelectionIndex] = React.useState(0);
  return (
    <ReactVirtualizedAutoSizer>
      {({ height, width }: any) => (
        <FixedSizeList
          className="List"
          height={height}
          width={width}
          itemData={[selectedIndex, setSelectionIndex]}
          itemCount={100}
          itemSize={50}
          onScroll={(props) => {
            itemPageModel.scrollOffset = props.scrollOffset;
          }}
          initialScrollOffset={itemPageModel.scrollOffset}
        >
          {renderRow}
        </FixedSizeList>
      )}
    </ReactVirtualizedAutoSizer>
  );
}
export default BookItemList;
