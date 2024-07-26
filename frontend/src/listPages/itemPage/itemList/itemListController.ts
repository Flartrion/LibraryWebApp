import React from "react";
import Item from "../../../dataclasses/item";
import itemPageController from "../itemPageController";
import ItemPageTab from "../itemPageTabsEnum";
import itemViewModel from "../itemViewPage/itemViewModel";
import itemListModel from "./itemListModel";

class ItemListController {
  setItemSelection: React.Dispatch<React.SetStateAction<number>>;
  setItemsLoaded: React.Dispatch<React.SetStateAction<boolean>>;
  setViewedItem: React.Dispatch<React.SetStateAction<Item>>;
  changeSelection(newValue: number): void {
    this.setItemSelection(newValue);
    itemViewModel.item = itemListModel.items[newValue];
    itemViewModel.itemIndex = newValue;
    itemListModel.itemSelection = newValue;
    this.setViewedItem(itemListModel.items[newValue]);
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
          itemListModel.scrollOffset = 0;
          itemListModel.itemSelection = undefined;
          if (this.setItemsLoaded != undefined) this.setItemsLoaded(true);
          else itemPageController.updateModel(ItemPageTab.Items);
          // console.log(items);
        } else {
          console.log(body);
        }
      })
      .catch((reason) => {
        console.log(reason);
      });
  }

  getFilteredID(id: string) {
    const options = {
      method: "POST",
      headers: {
        "Accept-Encoding": "application/json",
      },
    };
    let responseStatus: number;
    const request = new Request("items/get/" + id, options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        if (responseStatus == 200) {
          const item: Item = JSON.parse(body);
          itemListModel.itemSelection = undefined;
          itemViewModel.item = item;
          itemViewModel.itemIndex = undefined;
          itemPageController.updateModel(ItemPageTab.Item);
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
