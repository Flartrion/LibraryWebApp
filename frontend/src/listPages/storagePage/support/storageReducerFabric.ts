import StorageActionEnum from "./storageActionEnum";

const storageReducerFabric = (attachedModel: any) => {
  const reducer: React.Reducer<any, any> = (state: any, action: any) => {
    const [actionName, payload]: [StorageActionEnum, any] = action;
    const newState = { ...state };
    switch (actionName) {
      case StorageActionEnum.id:
        if (payload.length <= 36) {
          newState.errField = undefined;
          newState.id = payload;
          if (attachedModel != null) attachedModel.id = payload;
        } else newState.errField = actionName;
        return newState;
      case StorageActionEnum.address:
        if (payload.length <= 50) {
          newState.errField = undefined;
          newState.address = payload;
          if (attachedModel != null) attachedModel.address = payload;
        } else newState.errField = actionName;
        return newState;
      case StorageActionEnum.processing:
        newState.processing = payload;
        return newState;
      default:
        console.log(
          "Unknown action: " + actionName + "! Reverting to previous state."
        );
        return state;
    }
  };
  return reducer;
};

export default storageReducerFabric;
