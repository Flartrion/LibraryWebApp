import { Box, Container, ListItem, Tab, Tabs } from "@mui/material";
import { useState } from "react";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { ListChildComponentProps, FixedSizeList } from "react-window";

// I'm soooo not making a different file for that.
const itemPageModel = {
  pageSelection: 0,
};

function renderRow(props: ListChildComponentProps) {
  const { index, style } = props;
  return (
    <ListItem style={style} key={index}>
      {"item"}
    </ListItem>
  );
}

function ItemPage({}: any) {
  const [selection, setSelection] = useState(itemPageModel.pageSelection);

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    itemPageModel.pageSelection = newValue;
    setSelection(newValue);
  }
  return (
    <Container>
      <Tabs value={selection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" key={0} />
        <Tab label="Filters" key={1} />
      </Tabs>
      {selection == 0 ? (
        <ReactVirtualizedAutoSizer>
          {({ height, width }: any) => (
            <FixedSizeList
              height={height}
              width={width}
              itemCount={1000}
              itemSize={35}
            >
              {renderRow}
            </FixedSizeList>
          )}
        </ReactVirtualizedAutoSizer>
      ) : (
        "A denial"
      )}
    </Container>
  );
}

export default ItemPage;
