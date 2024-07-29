import itemViewModel from "../itemViewModel";
import itemListModel from "../../itemList/itemListModel";
import ListTab from "../../../support/listTab";
import itemPageController from "../../itemPageController";

const itemDeleteController = {
  onSuccessAction: (e: React.MouseEvent<HTMLButtonElement>) => {},
  delete(id: string) {
    const options = {
      method: "DELETE",
    };
    let responseStatus: number;
    const request = new Request("items/" + id, options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        console.log(body);
      })
      .catch((reason) => {
        console.log(reason);
      })
      .finally(() => {
        if (responseStatus == 200) {
          itemViewModel.item = undefined;
          itemViewModel.itemIndex = undefined;
          itemListModel.itemsLoaded = false;
          itemListModel.itemSelection = undefined;
          itemListModel.items = undefined;
          this.onSuccessAction();
          itemPageController.updateModel(ListTab.Items);
        }
      });
  },
};

export default itemDeleteController;
