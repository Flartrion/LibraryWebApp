import ItemActionEnum from "./itemActionEnum";

const itemReducerFabric = (attachedModel: any) => {
  const reducer: React.Reducer<any, any> = (state: any, action: any) => {
    const [actionName, payload]: [ItemActionEnum, any] = action;
    const newState = { ...state };
    switch (actionName) {
      case ItemActionEnum.id:
        if (payload.length <= 36) {
          newState.errField = undefined;
          newState.id = payload;
          if (attachedModel != null) attachedModel.id = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.isbn:
        if (payload.length <= 20) {
          newState.errField = undefined;
          newState.isbn = payload;
          if (attachedModel != null) attachedModel.isbn = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.rlbc:
        if (payload.length <= 20) {
          newState.errField = undefined;
          newState.rlbc = payload;
          if (attachedModel != null) attachedModel.rlbc = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.title:
        if (payload.length <= 40) {
          newState.errField = undefined;
          newState.title = payload;
          if (attachedModel != null) attachedModel.title = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.authors:
        if (payload.length <= 60) {
          newState.errField = undefined;
          newState.authors = payload;
          if (attachedModel != null) attachedModel.authors = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.type:
        if (payload.length <= 20) {
          newState.errField = undefined;
          newState.type = payload;
          if (attachedModel != null) attachedModel.type = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.language:
        if (payload.length <= 20) {
          newState.errField = undefined;
          newState.language = payload;
          if (attachedModel != null) attachedModel.language = payload;
        } else newState.errField = actionName;
        return newState;
      case ItemActionEnum.details:
        newState.errField = undefined;
        newState.details = payload;
        if (attachedModel != null) attachedModel.details = payload;
        return newState;
      case ItemActionEnum.processing:
        newState.processing = payload;
        return newState;
    }
  };
  return reducer;
};

export default itemReducerFabric;
