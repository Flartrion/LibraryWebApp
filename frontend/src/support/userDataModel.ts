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
    this.userRole = parseInt(cookieWorker.extractCookie("userRole"));
    this.userTheme = parseInt(cookieWorker.extractCookie("userTheme"));
  }
}

const userDataModel = new UserDataModel();
export default userDataModel;
