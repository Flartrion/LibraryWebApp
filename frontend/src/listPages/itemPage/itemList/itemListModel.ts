import Item from "../../../dataclasses/item";

type ItemListModel = {
  items: Item[];
  itemSelection: number;
  scrollOffset: number;
};

const itemListModel: ItemListModel = {
  items: [],
  itemSelection: -1,
  scrollOffset: 0,
};

export default itemListModel;
