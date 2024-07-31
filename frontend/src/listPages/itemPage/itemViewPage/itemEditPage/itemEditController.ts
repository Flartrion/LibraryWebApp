import React from "react";
import itemListModel from "../../itemList/itemListModel";
import itemViewModel from "../itemViewModel";
import itemListController from "../../itemList/itemListController";
import Item from "../../../../dataclasses/item";
import ItemActionEnum from "../../support/itemActionEnum";

class ItemEditController {
  subscribedPageDispatch: React.Dispatch<any>;
  private setProcessing = (newValue: boolean) => {
    this.subscribedPageDispatch([ItemActionEnum.processing, newValue]);
  };

  submitHandler: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
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
        if (responseStatus == 200) {
          const editedItem: Item = Object.fromEntries(data.entries()) as Item;
          if (itemListController.setViewedState != undefined)
            itemListController.setViewedState(editedItem);
          itemListModel.items[itemListModel.selection] = editedItem;
          itemViewModel.item = editedItem;
        }
      })
      .catch((reason) => {
        console.log(reason);
      })
      .finally(() => {});
  };
}

const itemEditController = new ItemEditController();

export default itemEditController;
