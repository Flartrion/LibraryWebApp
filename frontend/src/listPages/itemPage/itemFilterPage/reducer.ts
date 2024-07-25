import itemFilterModel from "./itemFilterModel";

const reducer: React.Reducer<any, any> = (state: any, action: any) => {
  const [actionName, payload] = action;
  const newState = { ...state };
  switch (actionName) {
    case "id":
      if (payload.length <= 36) {
        newState.errField = "";
        newState.id = payload;
        itemFilterModel.id = payload;
      } else newState.errField = actionName;
      return newState;
    case "isbn":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.isbn = payload;
        itemFilterModel.isbn = payload;
      } else newState.errField = actionName;
      return newState;
    case "rlbc":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.rlbc = payload;
        itemFilterModel.rlbc = payload;
      } else newState.errField = actionName;
      return newState;
    case "title":
      if (payload.length <= 40) {
        newState.errField = "";
        newState.title = payload;
        itemFilterModel.title = payload;
      } else newState.errField = actionName;
      return newState;
    case "authors":
      if (payload.length <= 60) {
        newState.errField = "";
        newState.authors = payload;
        itemFilterModel.authors = payload;
      } else newState.errField = actionName;
      return newState;
    case "type":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.type = payload;
        itemFilterModel.type = payload;
      } else newState.errField = actionName;
      return newState;
    case "language":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.language = payload;
        itemFilterModel.language = payload;
      } else newState.errField = actionName;
      return newState;
    case "details":
      newState.errField = "";
      newState.details = payload;
      itemFilterModel.details = payload;
      return newState;
    case "processing":
      newState.processing = payload;
      return newState;
  }
};

export default reducer;
