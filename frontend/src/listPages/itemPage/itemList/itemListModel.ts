import Item from "../../../dataclasses/item";

const itemListModel: ListModel<Item> = {
  items: [],
  loaded: false,
  selection: undefined,
  scrollOffset: 0,
};

export default itemListModel;
