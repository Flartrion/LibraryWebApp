import Item from "../../dataclasses/item";

// Only here for type-safety.
type ItemPageModel = {
  tabSelection: ListTab;
};

const itemPageModel: ItemPageModel = {
  tabSelection: 0,
};
export default itemPageModel;
