import ReactGeneralController from "../../support/reactGeneralController"
import ListTab from "../support/listTab"
import userPageModel from "./userPageModel"

class UserPageController extends ReactGeneralController<ListTab> {
  updateModel(newValue: ListTab): void {
    super.updateView(newValue)
    userPageModel.tabSelection = newValue
  }
}

const userPageController = new UserPageController()
export default userPageController
