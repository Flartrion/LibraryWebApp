import {
  Avatar,
  Box,
  CircularProgress,
  ListItem,
  ListItemIcon,
} from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { FixedSizeList, ListChildComponentProps } from "react-window";
import usersPageModel from "./usersPageModel";
import User from "../../dataclasses/user";

function renderRow(props: ListChildComponentProps) {
  const { index, style, data } = props;
  const user: User = data[index];
  return (
    <ListItem style={style} key={index}>
      <ListItemIcon>
        <Avatar />
      </ListItemIcon>
      {"User " + index.toString()}
    </ListItem>
  );
}

// TODO: Gotta replace this with virtualized grid eventually if I want to implement avatars or something like that.
// Do I, though?
function UsersList({ usersLoaded }: any) {
  return usersLoaded ? (
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
          itemData={usersPageModel.users}
        >
          {renderRow}
        </FixedSizeList>
      )}
    </ReactVirtualizedAutoSizer>
  ) : (
    <Box>
      <CircularProgress size="100px" />
    </Box>
  );
}
export default UsersList;
