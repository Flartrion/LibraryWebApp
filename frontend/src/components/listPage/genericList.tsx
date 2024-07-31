import { CircularProgress } from "@mui/material";
import ReactVirtualizedAutoSizer from "react-virtualized-auto-sizer";
import { FixedSizeList } from "react-window";
import { FC, useEffect, useMemo, useState } from "react";
import GenericListController from "./genericListController";

interface GenericVirtualListProps<T extends Id> {
  renderRow: FC;
  listController: GenericListController<T>;
  listModel: ListModel<T>;
}
// TODO: Gotta replace this with virtualized grid or something eventually if I want to implement pictures or something like that.
// Do I, though?
function GenericVirtualList<T extends Id>({
  renderRow,
  listController,
  listModel,
}: GenericVirtualListProps<T>) {
  const [selectedIndex, setItemSelection] = useState(listModel.selection);
  const [itemsLoaded, setLoaded] = useState(listModel.loaded);
  const data: [number, T[]] = useMemo(
    () => [selectedIndex, listModel.items],
    [selectedIndex, listModel.items]
  );

  useEffect(() => {
    listController.setSelectionState = setItemSelection;
    listController.setLoadedState = setLoaded;
    if (!listModel.loaded) {
      listController.getInitial();
      //TODO: Get the controller to actually load stuff
    }
    return () => {
      listController.setSelectionState = undefined;
      listController.setLoadedState = undefined;
    };
  }, [listController]);

  return (
    <ReactVirtualizedAutoSizer>
      {({ height, width }: any) =>
        itemsLoaded ? (
          <FixedSizeList
            className="List"
            height={height}
            width={width}
            itemData={data}
            itemCount={listModel.items.length}
            itemSize={50}
            itemKey={(index, data) => {
              return data[1][index].id;
            }}
            onScroll={(props) => {
              listModel.scrollOffset = props.scrollOffset;
            }}
            initialScrollOffset={listModel.scrollOffset}
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
export default GenericVirtualList;
