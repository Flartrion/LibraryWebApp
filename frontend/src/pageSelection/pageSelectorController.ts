import ReactGeneralController from "../support/reactGeneralController"
import pageSelectorModel from "./pageSelectorModel"

class PageSelectorController extends ReactGeneralController<number> {
  updateModel(newValue: number): void {
    super.updateView(newValue)
    pageSelectorModel.pageSelection = newValue
  }
}

const pageSelectorController = new PageSelectorController()
export default pageSelectorController
