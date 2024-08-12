import GenericListController from "../../../components/listPage/genericListController"
import User, { newUser } from "../../../dataclasses/user"
import userViewModel from "../userViewPage/userViewModel"
import userPageController from "../userPageController"
import userListModel from "./userListModel"

const userListController = new GenericListController<User>(
  "users/get",
  userListModel,
  userViewModel,
  userPageController,
  newUser
)
export default userListController
