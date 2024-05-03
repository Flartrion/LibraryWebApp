import stateSubscriberEntry from "./stateSubscriberEntry";

// Just general properties any single useState controller would need. Gotta think some more on useReducer()
abstract class ReactGeneralController<T> {
  subscribers: stateSubscriberEntry<T>[];
  updateView(newValue: T): void {
    for (let subscribers of this.subscribers) {
      subscribers[1](newValue);
    }
  }
  updateModel(newValue: T) {
    this.updateView(newValue);
  }
  subscribeView(
    name: string,
    stateFun: React.Dispatch<React.SetStateAction<T>>
  ): void {
    this.subscribers.push([name, stateFun]);
  }
  unsubscribeView(name: string) {
    const indexToRemove = this.subscribers.findIndex((entry) => {
      entry[0] === name;
    });
    if (indexToRemove > -1) this.subscribers.splice(indexToRemove, 1);
  }

  constructor() {
    this.subscribers = [];
  }
}
export default ReactGeneralController;
