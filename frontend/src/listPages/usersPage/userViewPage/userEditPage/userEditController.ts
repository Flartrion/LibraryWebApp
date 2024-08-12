import userListModel from "../../userList/userListModel"
import userViewModel from "../userViewModel"
import userListController from "../../userList/userListController"
import GenericEditController from "../../../../components/viewPage/editPage/genericEditController"
import User from "../../../../dataclasses/user"
import UserActionEnum from "../../support/userActionEnum"

const userEditController = new GenericEditController<User>(
  "users/update",
  userListModel,
  userListController,
  userViewModel,
  UserActionEnum
)

export default userEditController
