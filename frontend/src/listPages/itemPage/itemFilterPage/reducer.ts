import itemReducerFabric from "../itemReducerFabric";
import itemFilterModel from "./itemFilterModel";

const reducer: React.Reducer<any, any> = itemReducerFabric(itemFilterModel);

export default reducer;
