import itemListModel from "../../itemList/itemListModel";
import itemViewModel from "../itemViewModel";
import itemListController from "../../itemList/itemListController";
import ItemActionEnum from "../../support/itemActionEnum";
import GenericEditController from "../../../../components/viewPage/editPage/genericEditController";

const itemEditController = new GenericEditController(
  itemListModel,
  itemListController,
  itemViewModel,
  ItemActionEnum
);

export default itemEditController;
