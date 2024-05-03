import Item from "../dataclasses/item";

type ItemPageModel = {
  itemSelection: number;
  tabSelection: number;
  items: Item[];
};

const itemPageModel: ItemPageModel = {
  itemSelection: 0,
  tabSelection: 0,
  items: [],
};
export default itemPageModel;
