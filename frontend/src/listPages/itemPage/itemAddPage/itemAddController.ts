import GeneralAddController from "../../../components/addPage/generalAddController";
import itemListController from "../itemList/itemListController";
import itemAddModel from "./itemAddModel";

const itemAddController = new GeneralAddController(
  "items/new",
  itemAddModel,
  itemListController
);

export default itemAddController;
