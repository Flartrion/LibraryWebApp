/**
 * The class from which simple (independent from all other DB tables)
 * add-data controllers should be inherited
 */
abstract class AbstractAddController {
  /** Is supposed to have all the fields of inputted data except for ID */
  model: any;
  /** URL for backend api access */
  postURL: string;
  subscribedPageDispatch: React.Dispatch<any>;
  protected setProcessing(newValue: boolean) {
    this.subscribedPageDispatch(["processing", newValue]);
  }

  constructor(postURL: string, model: any) {
    this.postURL = postURL;
    this.model = model;
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
          this.model.itemsLoaded = false;
        }
        this.setProcessing(false);
      });
  }
}

export default AbstractAddController;
