const itemReducerFabric = (attachedModel: any) => {
  const reducer: React.Reducer<any, any> = (state: any, action: any) => {
    const [actionName, payload] = action;
    const newState = { ...state };
    switch (actionName) {
      case "id":
        if (payload.length <= 36) {
          newState.errField = "";
          newState.id = payload;
          if (attachedModel != null) attachedModel.id = payload;
        } else newState.errField = actionName;
        return newState;
      case "isbn":
        if (payload.length <= 20) {
          newState.errField = "";
          newState.isbn = payload;
          if (attachedModel != null) attachedModel.isbn = payload;
        } else newState.errField = actionName;
        return newState;
      case "rlbc":
        if (payload.length <= 20) {
          newState.errField = "";
          newState.rlbc = payload;
          if (attachedModel != null) attachedModel.rlbc = payload;
        } else newState.errField = actionName;
        return newState;
      case "title":
        if (payload.length <= 40) {
          newState.errField = "";
          newState.title = payload;
          if (attachedModel != null) attachedModel.title = payload;
        } else newState.errField = actionName;
        return newState;
      case "authors":
        if (payload.length <= 60) {
          newState.errField = "";
          newState.authors = payload;
          if (attachedModel != null) attachedModel.authors = payload;
        } else newState.errField = actionName;
        return newState;
      case "type":
        if (payload.length <= 20) {
          newState.errField = "";
          newState.type = payload;
          if (attachedModel != null) attachedModel.type = payload;
        } else newState.errField = actionName;
        return newState;
      case "language":
        if (payload.length <= 20) {
          newState.errField = "";
          newState.language = payload;
          if (attachedModel != null) attachedModel.language = payload;
        } else newState.errField = actionName;
        return newState;
      case "details":
        newState.errField = "";
        newState.details = payload;
        if (attachedModel != null) attachedModel.details = payload;
        return newState;
      case "processing":
        newState.processing = payload;
        return newState;
    }
  };
  return reducer;
};

export default itemReducerFabric;
