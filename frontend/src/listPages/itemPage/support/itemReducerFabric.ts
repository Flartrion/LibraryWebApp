import ItemActionEnum from "./itemActionEnum";

/**
 * Is used to create reducers that use different models for ability of
 * the app to memorize tab states while user uses other tabs
 * @param attachedModel The model used for memory
 * @returns See {@link React.Reducer}
 */
const itemReducerFabric = (attachedModel: any) => {
  const reducer: React.Reducer<any, any> = (state: any, action: any) => {
    const [actionName, payload]: [ItemActionEnum, any] = action;
    const newState = { ...state };
    switch (actionName) {
      case ItemActionEnum.id:
        if (payload.length <= 36) {
          newState.errField = null;
          newState.id = payload;
          if (attachedModel != null) attachedModel.id = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.isbn:
        if (payload.length <= 20) {
          newState.errField = null;
          newState.isbn = payload;
          if (attachedModel != null) attachedModel.isbn = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.rlbc:
        if (payload.length <= 20) {
          newState.errField = null;
          newState.rlbc = payload;
          if (attachedModel != null) attachedModel.rlbc = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.title:
        if (payload.length <= 40) {
          newState.errField = null;
          newState.title = payload;
          if (attachedModel != null) attachedModel.title = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.authors:
        if (payload.length <= 60) {
          newState.errField = null;
          newState.authors = payload;
          if (attachedModel != null) attachedModel.authors = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.type:
        if (payload.length <= 20) {
          newState.errField = null;
          newState.type = payload;
          if (attachedModel != null) attachedModel.type = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.language:
        if (payload.length <= 20) {
          newState.errField = null;
          newState.language = payload;
          if (attachedModel != null) attachedModel.language = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.details:
        newState.errField = null;
        newState.details = payload;
        if (attachedModel != null) attachedModel.details = payload;
        return newState;
      case ItemActionEnum.processing:
        newState.processing = payload;
        return newState;
      default:
        console.log("Unknown action: " + actionName + "! Returning prevState");
        return state;
    }
  };
  return reducer;
};

export default itemReducerFabric;
