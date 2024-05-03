import { Check } from "@mui/icons-material";
import { Box, Divider, ListItem, ListItemIcon, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { ListChildComponentProps, FixedSizeList } from "react-window";
import ItemPageTab from "./itemPageTabsEnum";
import itemPageController from "./itemPageController";

// I'm soooo not making a different file for that.
const itemPageModel = {
  pageSelection: 0,
};

function renderRow(props: ListChildComponentProps) {
  const { index, style } = props;
  return (
    <ListItem style={style} key={index}>
      <ListItemIcon>
        <Check />
      </ListItemIcon>
      {"item"}
    </ListItem>
  );
}

function ItemPage({ adminRights }: any) {
  const [tabSelection, setTabSelection] = useState(itemPageModel.pageSelection);

  useEffect(() => {
    itemPageController.subscribeView("itemPage", setTabSelection);
    return () => {
      itemPageController.unsubscribeView("itemPage");
    };
  });

  function handleSelection(
    e: React.SyntheticEvent<Element, Event>,
    newValue: number
  ) {
    itemPageModel.pageSelection = newValue;
    itemPageController.updateModel(newValue);
  }

  function SelectedPage({ pageSelection }: any) {
    switch (pageSelection) {
      case ItemPageTab.Items:
        return (
          // TODO: Gotta extract this to it's own file, as this would become ridiculous in layering after actual functionality would have been implemented.
          <ReactVirtualizedAutoSizer>
            {({ height, width }: any) => (
              <FixedSizeList
                className="List"
                height={height}
                width={width}
                itemCount={100}
                itemSize={50}
              >
                {renderRow}
              </FixedSizeList>
            )}
          </ReactVirtualizedAutoSizer>
        );
      case ItemPageTab.Filters:
        return "No";
      case ItemPageTab.Item:
        return "very no";
    }
  }

  return (
    <Box height={"80vh"}>
      <Tabs value={tabSelection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Results" key={ItemPageTab.Items} />
        <Tab label="Filters" key={ItemPageTab.Filters} />
        <Tab label="Item" key={ItemPageTab.Item} />
        {adminRights ? <Tab label="Add Item" key={ItemPageTab.AddItem} /> : ""}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default ItemPage;
