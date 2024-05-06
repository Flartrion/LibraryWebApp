import { Dispatch, SetStateAction } from "react";
import ReactGeneralController from "../support/reactGeneralController";
import credentialsHolder from "./credentialsHolder";

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
    console.log(form);

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

const loginObjectTempName = new LoginController();
export default loginObjectTempName;
