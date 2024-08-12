import GenericDeleteController from "../../../../components/viewPage/deleteDialog/genericDeleteController"
import storageListModel from "../../storageListPage/storageListModel"
import storagePageController from "../../storagePageController"
import storageViewModel from "../storageViewModel"

const storageDeleteController = new GenericDeleteController(
  "storages",
  storageViewModel,
  storageListModel,
  storagePageController
)
export default storageDeleteController
