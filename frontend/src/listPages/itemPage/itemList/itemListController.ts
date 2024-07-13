import ReactGeneralController from "../../../support/reactGeneralController";
import itemListModel from "./itemListModel";


class ItemListController extends ReactGeneralController<number> {
  updateModel(newValue: number): void {
    super.updateView(newValue);
    itemListModel.itemSelection = newValue;
  }
}

const itemListController = new ItemListController();
export default itemListController;
