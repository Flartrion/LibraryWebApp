import React from "react"
import ListTab from "../../listPages/support/listTab"

/**
 * Type T would be the type of row being sent back and forth from front- to back-end and vice versa
 */
class GenericListController<T extends Id> {
  setSelectionState: React.Dispatch<React.SetStateAction<number>>
  setLoadedState: React.Dispatch<React.SetStateAction<boolean>>
  setViewedState: React.Dispatch<React.SetStateAction<T>>

  postURL: string
  listModel: GenericListModel<T>
  viewModel: any
  pageController: any
  emptyItemGen: () => T

  constructor(
    postURL: string,
    listModel: GenericListModel<T>,
    viewModel: any,
    pageController: any,
    emptyItemGen: () => T
  ) {
    this.postURL = postURL
    this.listModel = listModel
    this.viewModel = viewModel
    this.pageController = pageController
    this.emptyItemGen = emptyItemGen
  }

  setLoaded(newValue: boolean): void {
    if (this.setLoadedState != undefined) this.setLoadedState(newValue)
    this.listModel.loaded = newValue
  }

  changeSelection(newValue: number): void {
    this.setSelectionState(newValue)
    this.viewModel.item = this.listModel.items[newValue]
    this.viewModel.itemIndex = newValue
    this.listModel.selection = newValue
    this.setViewedState(this.listModel.items[newValue])
    this.pageController.updateModel(ListTab.View)
  }

  getFiltered(filters: T) {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(filters),
    }
    let responseStatus: number
    const request = new Request(this.postURL, options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          const items: T[] = JSON.parse(body)
          this.listModel.items = items
          this.listModel.loaded = true
          this.listModel.scrollOffset = 0
          this.listModel.selection = undefined
          if (this.setLoadedState != undefined) this.setLoadedState(true)
          else this.pageController.updateModel(ListTab.Items)
          // console.log(items);
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }

  getFilteredID(id: string) {
    const options = {
      method: "POST",
      headers: {
        "Accept-Encoding": "application/json",
      },
    }
    let responseStatus: number
    const request = new Request(this.postURL + "/" + id, options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          const item: T = JSON.parse(body)
          this.listModel.selection = undefined
          this.viewModel.item = item
          this.viewModel.itemIndex = undefined
          this.pageController.updateModel(ListTab.Items)
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }

  getInitial() {
    const data = this.emptyItemGen()
    // console.log(data);
    this.getFiltered(data)
  }
}

export default GenericListController
