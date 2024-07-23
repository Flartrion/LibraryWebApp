import Item from "../../../dataclasses/item";
import itemPageController from "../itemPageController";
import ItemPageTab from "../itemPageTabsEnum";
import itemViewModel from "../itemViewPage/itemViewModel";
import itemListModel from "./itemListModel";

class ItemListController {
  setItemSelection: React.Dispatch<React.SetStateAction<number>>;
  setItemsLoaded: React.Dispatch<React.SetStateAction<boolean>>;
  changeSelection(newValue: number): void {
    this.setItemSelection(newValue);
    itemViewModel.item = itemListModel.items[newValue];
    itemViewModel.itemIndex = newValue;
    itemListModel.itemSelection = newValue;
    itemPageController.updateModel(ItemPageTab.Item);
  }

  getFiltered(filters: Item) {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(filters),
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
          itemListModel.itemsLoaded = true;
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

  getInitial() {
    const data: Item = {
      id: "",
      isbn: "",
      rlbc: "",
      authors: "",
      details: "",
      type: "",
      language: "",
      title: "",
    };
    this.getFiltered(data);
  }
}

const itemListController = new ItemListController();
export default itemListController;
