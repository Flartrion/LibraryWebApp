import ReactGeneralController from "../../support/reactGeneralController";
import ListTab from "../support/listTab";
import itemPageModel from "./itemPageModel";

class ItemPageController extends ReactGeneralController<ListTab> {
  updateModel(newValue: ListTab): void {
    super.updateView(newValue);
    itemPageModel.tabSelection = newValue;
  }
}

const itemPageController = new ItemPageController();
export default itemPageController;
