import itemListModel from "../itemList/itemListModel";

class ItemAddController {
  subscribedPageDispatch: React.Dispatch<any>;
  setProcessing(newValue: boolean) {
    this.subscribedPageDispatch(["processing", newValue]);
  }

  submit(data: FormData) {
    this.setProcessing(true);
    data.append("id", "0");
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    };

    let responseStatus: number;
    const request = new Request("items/new", options);
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
        if (responseStatus == 201) {
          itemListModel.itemsLoaded = false;
        }
        this.setProcessing(false);
      });
  }
}

const itemAddController = new ItemAddController();

export default itemAddController;
