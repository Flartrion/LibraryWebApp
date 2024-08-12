import GenericAddController from "../../../components/addPage/genericAddController"
import itemListController from "../itemList/itemListController"
import itemAddModel from "./itemAddModel"

const itemAddController = new GenericAddController(
  "items/new",
  itemAddModel,
  itemListController
)

export default itemAddController
