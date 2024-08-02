import ListTab from "../../../listPages/support/listTab";
import ReactGeneralController from "../../../support/reactGeneralController";

class GenericDeleteController<T extends Id> {
  postURL: string;
  viewModel: GenericViewModel<T>;
  listModel: GenericListModel<T>;
  pageController: ReactGeneralController<ListTab>;

  constructor(
    postURL: string,
    viewModel: GenericViewModel<T>,
    listModel: GenericListModel<T>,
    pageController: ReactGeneralController<ListTab>
  ) {
    this.postURL = postURL;
    this.viewModel = viewModel;
    this.listModel = listModel;
    this.pageController = pageController;
  }

  onSuccessAction: () => void;
  delete = (id: string) => {
    const options = {
      method: "DELETE",
    };
    let responseStatus: number;
    const request = new Request(this.postURL + "/" + id, options);
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
          this.viewModel.item = undefined;
          this.viewModel.index = undefined;
          this.listModel.loaded = false;
          this.listModel.selection = undefined;
          this.listModel.items = undefined;
          this.onSuccessAction();
          this.pageController.updateModel(ListTab.Items);
        }
      });
  };
}

export default GenericDeleteController;
