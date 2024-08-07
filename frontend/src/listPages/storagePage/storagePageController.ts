import ReactGeneralController from "../../support/reactGeneralController";
import ListTab from "../support/listTab";
import storagePageModel from "./storagePageModel";

class StoragePageController extends ReactGeneralController<ListTab> {
  updateModel(newValue: ListTab): void {
    super.updateView(newValue);
    storagePageModel.tabSelection = newValue;
  }
}

const storagePageController = new StoragePageController();
export default storagePageController;
