import { json } from "stream/consumers";
import ReactGeneralController from "../../support/reactGeneralController";
import usersPageModel from "./usersPageModel";

class UsersPageController extends ReactGeneralController<number> {
  fetchUsers(): void {
    const options = {
      method: "GET",
      headers: {
        // "WWW-Authenticate": "GeneralAuth",
        "Accept-Encoding": "application/json",
      },
    };
    const request = new Request("users", options);
    fetch("users")
      .then((result) => {
        return result.json;
      })
      .then((result) => {
        console.log(result.prototype);
      })
      .catch((reason) => {
        console.log(reason);
      });
  }

  setLoadingHandler: React.Dispatch<React.SetStateAction<boolean>>;
  setLoaded(value: boolean) {
    usersPageModel.usersLoaded = value;
    this.setLoadingHandler(value);
  }

  updateModel(newValue: number): void {
    super.updateView(newValue);
    usersPageModel.tabSelection = newValue;
  }
}

const usersPageController = new UsersPageController();
export default usersPageController;
