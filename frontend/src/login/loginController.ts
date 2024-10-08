import itemPageController from "../listPages/itemPage/itemPageController"
import storagePageController from "../listPages/storagePage/storagePageController"
import ListTab from "../listPages/support/listTab"
import PageSelection from "../pageSelection/pageEnum"
import pageSelectorController from "../pageSelection/pageSelectorController"
import ReactGeneralController from "../support/reactGeneralController"
import userDataModel from "../support/userDataModel"

class LoginController extends ReactGeneralController<Boolean> {
  loginRequest(login: string, password: string): void {
    const form = new FormData()
    form.set("email", login)
    form.set("password", password)
    const options = {
      method: "POST",
      headers: {
        "Accept-Encoding": "application/json",
      },
      body: form,
    }

    let responseStatus: number
    const request = new Request("login/plain", options)
    fetch(request)
      .then((response) => {
        responseStatus = response.status
        return response.text()
      })
      .then((resText) => {
        console.log(resText)
        this.updateModel(false)
      })
      .catch((reason) => {
        console.log(reason)
        this.updateModel(false)
      })
      .finally(() => {
        if (responseStatus === 200) {
          pageSelectorController.updateModel(PageSelection.Items)
          userDataModel.update()
        } else if (responseStatus === 401) this.updateModel(false)
      })
  }

  logout() {
    document.cookie =
      "userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    document.cookie =
      "userRole=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    document.cookie = "userID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    document.cookie = "JWTAuth=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"

    pageSelectorController.updateModel(PageSelection.Login)
    itemPageController.updateModel(ListTab.Items)
    storagePageController.updateModel(ListTab.Items)
    userDataModel.update()
  }
}

const loginController = new LoginController()
export default loginController
