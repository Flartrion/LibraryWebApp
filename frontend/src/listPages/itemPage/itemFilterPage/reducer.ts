import itemReducerFabric from "../support/itemReducerFabric"
import itemFilterModel from "./itemFilterModel"

const reducer: React.Reducer<any, any> = itemReducerFabric(itemFilterModel)

export default reducer
