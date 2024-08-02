import itemViewModel from "../itemViewModel";
import itemListModel from "../../itemList/itemListModel";
import itemPageController from "../../itemPageController";
import GenericDeleteController from "../../../../components/viewPage/deleteDialog/genericDeleteController";

const itemDeleteController = new GenericDeleteController(
  "items",
  itemViewModel,
  itemListModel,
  itemPageController
);

export default itemDeleteController;
