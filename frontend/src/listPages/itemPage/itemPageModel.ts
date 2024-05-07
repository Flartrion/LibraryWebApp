import Item from "../../dataclasses/item";

// Only here for type-safety.
type ItemPageModel = {
  scrollOffset: number;
  itemSelection: number;
  tabSelection: number;
  items: Item[];
};

const itemPageModel: ItemPageModel = {
  itemSelection: -1,
  tabSelection: 0,
  scrollOffset: 0,
  items: [],
};
export default itemPageModel;
