import GenericAddController from "../../../components/addPage/genericAddController";
import storageListController from "../storageListPage/storageListController";
import storageAddModel from "./storageAddModel";

const storageAddController = new GenericAddController(
  "storages/new",
  storageAddModel,
  storageListController
);
export default storageAddController;
