import { newItem } from "../../../dataclasses/item";
import itemPageController from "../itemPageController";
import itemViewModel from "../itemViewPage/itemViewModel";
import itemListModel from "./itemListModel";
import GenericListController from "../../../components/listPage/genericListController";
import Item from "../../../dataclasses/item";

const itemListController = new GenericListController<Item>(
  "items/get",
  itemListModel,
  itemViewModel,
  itemPageController,
  newItem
);
export default itemListController;
