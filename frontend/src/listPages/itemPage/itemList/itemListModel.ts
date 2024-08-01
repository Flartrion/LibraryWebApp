import Item from "../../../dataclasses/item";

const itemListModel: GenericListModel<Item> = {
  items: [],
  loaded: false,
  selection: undefined,
  scrollOffset: 0,
};

export default itemListModel;
