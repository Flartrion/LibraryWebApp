import ItemBalance, {
  newItemBalance,
} from "../../../../dataclasses/itemBalance"
import Storage, { newStorage } from "../../../../dataclasses/storage"
import itemBalanceModel from "./itemBalanceModel"

class ItemBalanceController {
  storageFilter: string = ""

  dialogOnCancel: () => void = undefined
  dialogSetStoragesLoadedState: React.Dispatch<React.SetStateAction<boolean>> =
    undefined
  viewSetStoragesLoadedState: React.Dispatch<React.SetStateAction<boolean>> =
    undefined
  setVisibleEntriesState: React.Dispatch<React.SetStateAction<ItemBalance[]>> =
    undefined

  loadStorages = () => {
    const filters = newStorage()
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(filters),
    }
    let responseStatus: number
    const request = new Request("storages/get", options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          const storages: Storage[] = JSON.parse(body)
          //   console.log(storages);
          itemBalanceModel.storages = storages
          itemBalanceModel.storagesLoaded = true
          //   console.log("finished loading");
          //   console.log(this);
          //   console.log(this.setStoragesLoadedState);
          if (this.dialogSetStoragesLoadedState != undefined)
            this.dialogSetStoragesLoadedState(true)
          if (this.viewSetStoragesLoadedState != undefined)
            this.viewSetStoragesLoadedState(true)
          // console.log(items);
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }

  unloadStorages = () => {
    itemBalanceModel.storages = undefined
    itemBalanceModel.storagesLoaded = false
  }

  filterByStorage = (idStorage: string) => {
    this.storageFilter = idStorage
    if (idStorage != "") {
      itemBalanceModel.historyEntriesFiltered =
        itemBalanceModel.historyEntries.filter(
          (value) => value.idStorage == idStorage
        )
    } else
      itemBalanceModel.historyEntriesFiltered =
        itemBalanceModel.historyEntries.slice()
    this.setVisibleEntriesState(itemBalanceModel.historyEntriesFiltered)
  }

  private deleteEntryLocal = (idEntry: string) => {
    itemBalanceModel.historyEntries.splice(
      itemBalanceModel.historyEntries.findIndex((value) => value.id == idEntry)
    )
    if (this.storageFilter != "") {
      itemBalanceModel.historyEntriesFiltered.splice(
        itemBalanceModel.historyEntriesFiltered.findIndex(
          (value) => value.id == idEntry
        )
      )
    } else
      itemBalanceModel.historyEntriesFiltered =
        itemBalanceModel.historyEntries.slice()
    this.setVisibleEntriesState(itemBalanceModel.historyEntriesFiltered)
  }

  private addEntryLocal = (item: ItemBalance) => {
    const sorter = (a: ItemBalance, b: ItemBalance) => {
      if (a.date < b.date) return -1
      else if (a.date == b.date) return 0
      else return 1
    }

    itemBalanceModel.historyEntries.push(item)
    itemBalanceModel.historyEntries.sort(sorter)
    if (this.storageFilter != "") {
      itemBalanceModel.historyEntriesFiltered.push(item)
      itemBalanceModel.historyEntriesFiltered.sort(sorter)
    } else
      itemBalanceModel.historyEntriesFiltered =
        itemBalanceModel.historyEntries.slice()

    this.setVisibleEntriesState(itemBalanceModel.historyEntriesFiltered)
  }

  newBalanceEntry = (data: FormData) => {
    // TODO: Make it set state to "unloaded" so that newly added entry would be loaded
    // console.log("Sending: " + JSON.stringify(Object.fromEntries(data)));
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    }
    let responseStatus: number
    const request = new Request("bankHistory/new", options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 201) {
          // console.log(body)
          this.addEntryLocal(JSON.parse(body) as ItemBalance)
          this.dialogOnCancel()
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }

  deleteBalanceEntry = (id: string) => {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
    }
    let responseStatus: number
    const request = new Request("bankHistory/delete/" + id, options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          // console.log(body)
          this.deleteEntryLocal(id)
          this.dialogOnCancel()
          console.log("Successfuly deleted entry " + id)
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }

  loadEntries = (id: string) => {
    const data = newItemBalance("", id)
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(data),
    }
    let responseStatus: number
    const request = new Request("bankHistory/get", options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          // console.log(body)
          itemBalanceModel.historyEntries = JSON.parse(body) as ItemBalance[]
          itemBalanceModel.historyEntriesFiltered =
            itemBalanceModel.historyEntries.slice()
          itemBalanceModel.id = id
          this.setVisibleEntriesState(itemBalanceModel.historyEntries)
        } else if (responseStatus == 404) {
          itemBalanceModel.historyEntries = []
          itemBalanceModel.historyEntriesFiltered =
            itemBalanceModel.historyEntries.slice()
          itemBalanceModel.id = id
          console.log(body)
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }
}

const itemBalanceController = new ItemBalanceController()

export default itemBalanceController
