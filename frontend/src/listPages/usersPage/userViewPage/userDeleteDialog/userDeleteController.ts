import GenericDeleteController from "../../../../components/viewPage/deleteDialog/genericDeleteController";
import User from "../../../../dataclasses/user";
import userViewModel from "../userViewModel";
import userListModel from "../../userList/userListModel";
import userPageController from "../../userPageController";

const userDeleteController = new GenericDeleteController<User>(
  "users",
  userViewModel,
  userListModel,
  userPageController
);

export default userDeleteController;
