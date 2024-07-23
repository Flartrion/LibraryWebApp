import cookieWorker from "./cookieWorker";

/**
 * Storage for user data extracted from cookies, 
 * for the purpose of reducing unneccessary regexp operations 
 */
class UserDataModel {
  userName: String;
  userRole: number;
  userTheme: number;

  constructor() {
    this.update();
  }

  update() {
    this.userName = cookieWorker.extractCookie("userName");

    let userRole = parseInt(cookieWorker.extractCookie("userRole"));
    if (!Number.isNaN(userRole)) this.userRole = userRole;
    else this.userRole = undefined;

    let userTheme = parseInt(cookieWorker.extractCookie("userTheme"));
    if (!Number.isNaN(userTheme)) this.userTheme = userTheme;
    else this.userTheme = 0;
  }
}

const userDataModel = new UserDataModel();
export default userDataModel;
