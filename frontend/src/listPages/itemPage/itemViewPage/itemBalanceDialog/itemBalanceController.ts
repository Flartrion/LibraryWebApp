import Storage, { newStorage } from "../../../../dataclasses/storage";
import itemBalanceModel from "./itemBalanceModel";

const itemBalanceController: {
  setStoragesLoadedState: React.Dispatch<React.SetStateAction<boolean>>;
  loadStorages: () => void;
  newBalanceEntry: (data: FormData) => void;
  onCancel: ({}: any) => void;
} = {
  onCancel: undefined,
  setStoragesLoadedState: undefined,
  loadStorages() {
    const filters = newStorage();
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(filters),
    };
    let responseStatus: number;
    const request = new Request("storages/get", options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        if (responseStatus == 200) {
          const storages: Storage[] = JSON.parse(body);
          //   console.log(storages);
          itemBalanceModel.storages = storages;
          itemBalanceModel.loaded = true;
          //   console.log("finished loading");
          //   console.log(this);
          //   console.log(this.setStoragesLoadedState);
          if (this.setStoragesLoadedState != undefined)
            this.setStoragesLoadedState(true);
          // console.log(items);
        } else {
          console.log(body);
        }
      })
      .catch((reason) => {
        console.log(reason);
      });
  },

  newBalanceEntry(data: FormData) {
    // console.log("Sending: " + JSON.stringify(Object.fromEntries(data)));
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    };
    let responseStatus: number;
    const request = new Request("bankHistory/new", options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        if (responseStatus == 201) {
          console.log(body);
          this.onCancel();
        } else {
          console.log(body);
        }
      })
      .catch((reason) => {
        console.log(reason);
      });
  },
};
export default itemBalanceController;
