import itemAddModel from "./itemAddModel";

const itemAddPageReducer: React.Reducer<any, any> = (
  state: any,
  action: any
) => {
  const [actionType, payload] = action;
  const newState = { ...state };
  switch (actionType) {
    case "isbn":
      newState.isbn = payload;
      itemAddModel.isbn = payload;
      return newState;
    case "rlbc":
      newState.rlbc = payload;
      itemAddModel.rlbc = payload;
      return newState;
    case "title":
      newState.title = payload;
      itemAddModel.title = payload;
      return newState;
    case "authors":
      newState.authors = payload;
      itemAddModel.authors = payload;
      return newState;
    case "type":
      newState.type = payload;
      itemAddModel.type = payload;
      return newState;
    case "language":
      newState.language = payload;
      itemAddModel.language = payload;
      return newState;
    case "details":
      newState.details = payload;
      itemAddModel.details = payload;
      return newState;
    case "processing":
      newState.processing = payload;
      return newState;
  }
};

export default itemAddPageReducer;
