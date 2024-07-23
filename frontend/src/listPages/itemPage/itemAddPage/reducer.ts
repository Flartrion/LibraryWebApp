import itemAddModel from "./itemAddModel";

const reducer: React.Reducer<any, any> = (
  state: any,
  action: any
) => {
  const [actionName, payload] = action;
  const newState = { ...state };
  switch (actionName) {
    case "isbn":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.isbn = payload;
        itemAddModel.isbn = payload;
      } else newState.errField = actionName;
      return newState;
    case "rlbc":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.rlbc = payload;
        itemAddModel.rlbc = payload;
      } else newState.errField = actionName;
      return newState;
    case "title":
      if (payload.length <= 40) {
        newState.errField = "";
        newState.title = payload;
        itemAddModel.title = payload;
      } else newState.errField = actionName;
      return newState;
    case "authors":
      if (payload.length <= 60) {
        newState.errField = "";
        newState.authors = payload;
        itemAddModel.authors = payload;
      } else newState.errField = actionName;
      return newState;
    case "type":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.type = payload;
        itemAddModel.type = payload;
      } else newState.errField = actionName;
      return newState;
    case "language":
      if (payload.length <= 20) {
        newState.errField = "";
        newState.language = payload;
        itemAddModel.language = payload;
      } else newState.errField = actionName;
      return newState;
    case "details":
      newState.errField = "";
      newState.details = payload;
      itemAddModel.details = payload;
      return newState;
    case "processing":
      newState.processing = payload;
      return newState;
  }
};

export default reducer;
