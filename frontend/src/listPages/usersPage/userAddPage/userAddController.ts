import GenericAddController from "../../../components/addPage/genericAddController";
import User from "../../../dataclasses/user";
import userAddModel from "./userAddModel";
import userListController from "../userList/userListController";

const userAddController = new GenericAddController<User>(
  "users/new",
  userAddModel,
  userListController
);

export default userAddController;
