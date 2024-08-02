import userReducerFabric from "../support/userReducerFabric";
import userAddModel from "./userAddModel";

const reducer: React.Reducer<any, any> = userReducerFabric(userAddModel);
export default reducer;
