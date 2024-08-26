class AvailabilityLoader {
  setLoadedState: React.Dispatch<React.SetStateAction<boolean>> = undefined

  loadAvailability(idItem: string) {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
    }
    let responseStatus: number
    const request = new Request("itemLocations/get/" + idItem, options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((body) => {
        if (responseStatus == 200) {
          const availability: any[] = JSON.parse(body)

          console.log("finished loading")
          console.log(availability)
          //   console.log(this);
          //   console.log(this.setStoragesLoadedState);
          // console.log(items);
        } else {
          console.log(body)
        }
      })
      .catch((reason) => {
        console.log(reason)
      })
  }
}

const availabilityLoader = new AvailabilityLoader()
export default availabilityLoader
