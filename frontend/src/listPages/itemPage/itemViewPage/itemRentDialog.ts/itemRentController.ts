class ItemRentController {
  newDialogClose: () => void = undefined

  newRentEntry = (data: FormData) => {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify(Object.fromEntries(data)),
    }
    let responseStatus: number
    const request = new Request("rents/new", options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 201) {
          console.log(body)
          if (this.newDialogClose != undefined) this.newDialogClose()
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }
}

const itemRentController = new ItemRentController()
export default itemRentController
