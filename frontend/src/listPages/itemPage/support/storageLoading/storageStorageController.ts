import React from "react"
import { newStorage } from "../../../../dataclasses/storage"
import storageStorageModel from "./storageStorageModel"

class StorageStorageController {
  setLoadedStates: React.Dispatch<React.SetStateAction<boolean>>[] = []

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
          storageStorageModel.storages = storages
          storageStorageModel.loaded = true

          this.setLoadedStates.forEach((value) => {
            value(true)
          })

          //   console.log("finished loading");
          //   console.log(this);
          //   console.log(this.setStoragesLoadedState);
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
    storageStorageModel.storages = undefined
    this.setLoadedStates.forEach((value) => {
      value(false)
    })
  }
}

const storageStorageController = new StorageStorageController()
export default storageStorageController
