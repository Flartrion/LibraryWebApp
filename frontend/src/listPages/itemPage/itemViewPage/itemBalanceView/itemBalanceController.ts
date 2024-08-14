import ItemBalance, {
  newItemBalance,
} from "../../../../dataclasses/itemBalance"
import Storage, { newStorage } from "../../../../dataclasses/storage"
import itemBalanceModel from "./itemBalanceModel"

class ItemBalanceController {
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

  newBalanceEntry = (data: FormData) => {
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
          itemBalanceModel.historyEntries.push(JSON.parse(body) as ItemBalance)
          this.setVisibleEntriesState(itemBalanceModel.historyEntries)
          this.dialogOnCancel()
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
          itemBalanceModel.id = id
          this.setVisibleEntriesState(itemBalanceModel.historyEntries)
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
