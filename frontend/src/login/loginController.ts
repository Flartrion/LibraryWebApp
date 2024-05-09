import ReactGeneralController from "../support/reactGeneralController";

class LoginController extends ReactGeneralController<Boolean> {
  loginRequest(login: string, password: string): void {
    const form = new FormData();
    form.set("email", login);
    form.set("password", password);
    const options = {
      method: "POST",
      headers: {
        "Accept-Encoding": "application/json",
      },
      body: form,
    };

    const request = new Request("login/plain", options);
    fetch(request)
      .then((response) => {
        console.log(response.statusText);
        return response.text();
      })
      .then((resText) => {
        console.log(resText);
        this.updateModel(false);
      })
      .catch((reason) => {
        console.log(reason);
        this.updateModel(false);
      });
  }
}

const loginController = new LoginController();
export default loginController;
