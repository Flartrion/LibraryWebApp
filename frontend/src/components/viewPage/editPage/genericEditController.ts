import { Dispatch, FormEvent, FormEventHandler } from "react"
import GenericListController from "../../listPage/genericListController"

class GenericEditController<T extends Id> {
  subscribedPageDispatch: Dispatch<any>

  protected postURL: string
  protected listModel: GenericListModel<T>
  protected listController: GenericListController<T>
  protected viewModel: GenericViewModel<T>
  protected enum: any

  constructor(
    postURL: string,
    listModel: GenericListModel<T>,
    listController: GenericListController<T>,
    viewModel: GenericViewModel<T>,
    enumUsed: any
  ) {
    this.postURL = postURL
    this.listModel = listModel
    this.listController = listController
    this.viewModel = viewModel
    this.enum = enumUsed
  }

  protected setProcessing = (newValue: boolean) => {
    this.subscribedPageDispatch([this.enum.processing, newValue])
  }

  submitHandler: FormEventHandler<HTMLFormElement> = (
    event: FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault()
    const data = new FormData(event.currentTarget)
    this.setProcessing(true)
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    }

    let responseStatus: number
    const request = new Request(
      this.postURL + "/" + data.get("id").toString(),
      options
    )
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((resText) => {
        console.log(resText)
        if (responseStatus == 200) {
          const editedItem: T = Object.fromEntries(data.entries()) as T
          if (this.listController.setViewedState != undefined)
            this.listController.setViewedState(editedItem)
          this.listModel.items[this.listModel.selection] = editedItem
          this.viewModel.item = editedItem
        } else this.setProcessing(false)
      })
      .catch((reason) => {
        console.log(reason)
      })
      .finally(() => {})
  }
}

export default GenericEditController
