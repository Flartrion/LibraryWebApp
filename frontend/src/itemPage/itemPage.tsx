import { Check } from "@mui/icons-material";
import { Box, Divider, ListItem, ListItemIcon, Tab, Tabs } from "@mui/material";
import { useEffect, useState } from "react";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { ListChildComponentProps, FixedSizeList } from "react-window";
import ItemPageTab from "./itemPageTabsEnum";
import itemPageController from "./itemPageController";
import itemPageModel from "./itemPageModel";

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
  const [tabSelection, setTabSelection] = useState(itemPageModel.tabSelection);

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
        {/* Don't show if no item is selected */}
        {itemPageModel.itemSelection > -1 ? (
          <Tab label="Item" key={ItemPageTab.Item} />
        ) : (
          ""
        )}
        {/* Don't show if no admin privilegies. */}
        {/* TODO: Admin privilegies */}
        {adminRights ? <Tab label="Add Item" key={ItemPageTab.AddItem} /> : ""}
      </Tabs>
      <Divider />
      <SelectedPage pageSelection={tabSelection} />
    </Box>
  );
}

export default ItemPage;
