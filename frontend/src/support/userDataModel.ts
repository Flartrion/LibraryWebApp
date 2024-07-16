import cookieWorker from "./cookieWorker";

class UserDataModel {
  userName: String;
  userRole: number;
  userTheme: number;

  constructor() {
    this.userName = cookieWorker.extractCookie("userName");
    this.userRole = parseInt(cookieWorker.extractCookie("userRole"));
    this.userTheme = parseInt(cookieWorker.extractCookie("userTheme"));
  }

  update() {
    this.userName = cookieWorker.extractCookie("userName");

    let userRole = parseInt(cookieWorker.extractCookie("userRole"));
    if (!Number.isNaN(userRole)) this.userRole = userRole;
    else this.userRole = undefined;

    let userTheme = parseInt(cookieWorker.extractCookie("userTheme"));
    if (!Number.isNaN(userTheme)) this.userTheme = userTheme;
    else this.userTheme = undefined;
  }
}

const userDataModel = new UserDataModel();
export default userDataModel;
