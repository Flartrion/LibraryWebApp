import itemReducerFabric from "../itemReducerFabric";
import itemAddModel from "./itemAddModel";

const reducer: React.Reducer<any, any> = itemReducerFabric(itemAddModel);
export default reducer;
