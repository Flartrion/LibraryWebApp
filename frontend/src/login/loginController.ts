import cookieWorker from "../support/credentialHolder";
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

    const request = new Request("login", options);
    fetch(request).then((response) => {
      if (response.status == 200) {
        console.log(response.statusText);
        response.text().then((resText) => {
          console.log(resText);
        });
      } else {
        console.log(response.statusText);
        response.text().then((resText) => {
          console.log(resText);
        });
      }
    });
    this.updateModel(false);
  }
}

const loginController = new LoginController();
export default loginController;
