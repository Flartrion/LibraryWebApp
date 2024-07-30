import ItemActionEnum from "../../listPages/itemPage/support/itemActionEnum";

/**
 * The class from which simple (independent from all other DB tables)
 * add-data controllers should be inherited
 */
class GeneralAddController {
  /** Is supposed to have all the fields of inputted data except for ID */
  model: any;
  /** URL for backend api access */
  postURL: string;
  /** Needed to update list state to "unloaded" so as new addition
   * would be loaded after next visit to list page */
  // TODO: Replace listController with abstract class type
  listController: any;
  subscribedPageDispatch: React.Dispatch<any>;
  protected setProcessing(newValue: boolean) {
    this.subscribedPageDispatch([ItemActionEnum.processing, newValue]);
  }

  // TODO: Replace listController with abstract class type
  /**
   *
   * @param postURL URL for backend api access
   * @param model Is supposed to have all the fields of inputted data except for ID
   * @param listController Needed to update list state to "unloaded"
   * so as new addition would be loaded after next visit to list page
   */
  constructor(postURL: string, model: any, listController: any) {
    this.postURL = postURL;
    this.model = model;
    this.listController = listController;
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
    const request = new Request(this.postURL, options);
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
          // TODO: Actually implement this function
          // this.listController.itemsLoaded = false;
        }
        this.setProcessing(false);
      });
  }
}

export default GeneralAddController;
