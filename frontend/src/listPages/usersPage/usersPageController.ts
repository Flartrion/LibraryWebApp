import ReactGeneralController from "../../support/reactGeneralController";
import usersPageModel from "./usersPageModel";

class UsersPageController extends ReactGeneralController<number> {
  fetchUsers(): void {
    
  }

  updateModel(newValue: number): void {
    super.updateView(newValue);
    usersPageModel.tabSelection = newValue;
  }
}

const usersPageController = new UsersPageController();
export default usersPageController;
