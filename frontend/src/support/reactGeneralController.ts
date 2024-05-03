// Just general properties any single useState controller would need. Gotta think some more on useReducer()
// Assemble several of those for more complicated states?
type ReactGeneralController<T> = {
  updateView: { (newValue: T): void };
  updateModel: { (newValue: T): void };
  subscribeView: {
    (name: string, stateFun: React.Dispatch<React.SetStateAction<T>>): void;
  };
  unsubscribeView: { (name: string): void };
};
export default ReactGeneralController;
