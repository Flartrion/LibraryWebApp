import React from "react";
import Item from "../../../../dataclasses/item";
import itemListModel from "../../itemList/itemListModel";
import itemViewModel from "../itemViewModel";
import itemPageController from "../../itemPageController";
import itemListController from "../../itemList/itemListController";

class ItemEditController {
  subscribedPageDispatch: React.Dispatch<any>;
  setEditState: React.Dispatch<React.SetStateAction<Boolean>>;
  private setProcessing(newValue: boolean) {
    this.subscribedPageDispatch(["processing", newValue]);
  }

  submit(data: FormData) {
    this.setProcessing(true);
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    };

    let responseStatus: number;
    const request = new Request("items/update", options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((resText) => {
        console.log(resText);
      })
      .catch((reason) => {
        console.log(reason);
      })
      .finally(() => {
        if (responseStatus == 200) {
          const editedItem: Item = Object.fromEntries(data.entries()) as Item;
          itemListModel.items[itemListModel.itemSelection] = editedItem;
          itemViewModel.item = editedItem;
          itemListController.setViewedItem(editedItem);
          this.setEditState(false);
        }
        this.setProcessing(false);
      });
  }
}

const itemEditController = new ItemEditController();

export default itemEditController;
