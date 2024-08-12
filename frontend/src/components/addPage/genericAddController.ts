import GenericListController from "../listPage/genericListController"

/**
 * The class from which simple (independent from all other DB tables)
 * add-data controllers should be inherited
 */
class GenericAddController<T extends Id> {
  /** Is supposed to have all the fields of inputted data except for ID */
  model: any
  /** URL for backend api access */
  postURL: string
  /** Needed to update list state to "unloaded" so as new addition
   * would be loaded after next visit to list page */
  listController: GenericListController<T>
  subscribedPageDispatch: React.Dispatch<any>
  setProcessing = (newValue: boolean) => {}

  /**
   *
   * @param postURL URL for backend api access
   * @param model Is supposed to have all the fields of inputted data except for ID
   * @param listController Needed to update list state to "unloaded"
   * so as new addition would be loaded after next visit to list page
   */
  constructor(
    postURL: string,
    model: any,
    listController: GenericListController<T>
  ) {
    this.postURL = postURL
    this.model = model
    this.listController = listController
  }

  submit(data: FormData) {
    this.setProcessing(true)
    data.append("id", "0")
    // console.log("Sending: " + JSON.stringify(Object.fromEntries(data)));
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    }

    let responseStatus: number
    const request = new Request(this.postURL, options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((resText) => {
        console.log(resText)
      })
      .catch((reason) => {
        console.log(reason)
      })
      .finally(() => {
        if (responseStatus == 201) {
          this.listController.setLoaded(false)
        }
        this.setProcessing(false)
      })
  }
}

export default GenericAddController
