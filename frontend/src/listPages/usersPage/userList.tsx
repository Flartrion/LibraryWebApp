import { Check } from "@mui/icons-material";
import { ListItem, ListItemIcon } from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { FixedSizeList, ListChildComponentProps } from "react-window";
import usersPageModel from "./usersPageModel";

function renderRow(props: ListChildComponentProps) {
  const { index, style } = props;
  return (
    <ListItem style={style} key={index}>
      <ListItemIcon>
        <Check />
      </ListItemIcon>
      {"User " + index.toString()}
    </ListItem>
  );
}

// TODO: Gotta replace this with virtualized grid eventually if I want to implement pictures or something like that.
// Do I, though?
function UserList() {
  return (
    <ReactVirtualizedAutoSizer>
      {({ height, width }: any) => (
        <FixedSizeList
          className="List"
          height={height}
          width={width}
          itemCount={100}
          itemSize={50}
          onScroll={(props) => {
            usersPageModel.scrollOffset = props.scrollOffset;
          }}
          initialScrollOffset={usersPageModel.scrollOffset}
        >
          {renderRow}
        </FixedSizeList>
      )}
    </ReactVirtualizedAutoSizer>
  );
}
export default UserList;
