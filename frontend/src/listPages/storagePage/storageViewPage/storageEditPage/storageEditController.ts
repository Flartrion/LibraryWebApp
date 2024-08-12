import GenericEditController from "../../../../components/viewPage/editPage/genericEditController"
import storageListController from "../../storageListPage/storageListController"
import storageListModel from "../../storageListPage/storageListModel"
import StorageActionEnum from "../../support/storageActionEnum"
import storageViewModel from "../storageViewModel"

const storageEditController = new GenericEditController(
  "storages/update",
  storageListModel,
  storageListController,
  storageViewModel,
  StorageActionEnum
)
export default storageEditController
