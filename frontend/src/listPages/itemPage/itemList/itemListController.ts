import Item from "../../../dataclasses/item";
import itemListModel from "./itemListModel";

class ItemListController {
  setItemSelection: React.Dispatch<React.SetStateAction<number>>;
  setItemsLoaded: React.Dispatch<React.SetStateAction<boolean>>;
  changeSelection(newValue: number): void {
    this.setItemSelection(newValue);
    itemListModel.itemSelection = newValue;
  }

  getFiltered(filters: Item) {
    // TODO
  }

  getInitial() {
    const data: Item & {
      id: "";
    } = {
      id: "",
      isbn: "",
      rlbc: "",
      authors: "",
      details: "",
      type: "",
      language: "",
      title: "",
    };
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(data),
    };
    let responseStatus: number;
    const request = new Request("items/get", options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        if (responseStatus == 200) {
          const items: Item[] = JSON.parse(body);
          itemListModel.items = items;
          this.setItemsLoaded(true);
          // console.log(items);
        } else {
          console.log(body);
        }
      })
      .catch((reason) => {
        console.log(reason);
      });
  }
}

const itemListController = new ItemListController();
export default itemListController;
