import ReactGeneralController from "../../support/reactGeneralController";
import itemPageModel from "./itemPageModel";

class ItemPageController extends ReactGeneralController<number> {
  updateModel(newValue: number): void {
    super.updateView(newValue);
    itemPageModel.tabSelection = newValue;
  }
}

const itemPageController = new ItemPageController();
export default itemPageController;
