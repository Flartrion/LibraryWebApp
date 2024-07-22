import Item from "../../../dataclasses/item";

type ItemListModel = {
  items: Item[];
  itemsLoaded: boolean;
  itemSelection: number;
  scrollOffset: number;
};

const itemListModel: ItemListModel = {
  items: [],
  itemsLoaded: false,
  itemSelection: -1,
  scrollOffset: 0,
};

export default itemListModel;
