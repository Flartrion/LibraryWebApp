import Item from "../../dataclasses/item";
import ListTab from "../support/listTab";

/** Only here for type-safety. */
type ItemPageModel = {
  tabSelection: ListTab;
};

const itemPageModel: ItemPageModel = {
  tabSelection: 0,
};
export default itemPageModel;
