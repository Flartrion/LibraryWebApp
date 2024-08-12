import GenericListController from "../../../components/listPage/genericListController"
import { newStorage } from "../../../dataclasses/storage"
import storagePageController from "../storagePageController"
import storageViewModel from "../storageViewPage/storageViewModel"
import storageListModel from "./storageListModel"

const storageListController = new GenericListController(
  "storages/get",
  storageListModel,
  storageViewModel,
  storagePageController,
  newStorage
)

export default storageListController
