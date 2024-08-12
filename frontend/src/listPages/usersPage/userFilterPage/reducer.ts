import userReducerFabric from "../support/userReducerFabric"
import userFilterModel from "./userFilterModel"

const reducer: React.Reducer<any, any> = userReducerFabric(userFilterModel)

export default reducer
