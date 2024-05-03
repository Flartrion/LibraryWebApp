import ReactGeneralController from "../support/reactGeneralController";
import stateSubscriberEntry from "../support/stateSubscriberEntry";
import itemPageModel from "./itemPageModel";

type ItemPageController = ReactGeneralController<number> & {
  subscribers: stateSubscriberEntry<number>[];
};

const itemPageController: ItemPageController = {
  subscribers: [],
  updateView: (newValue: number) => {
    for (let subscribers of itemPageController.subscribers) {
      subscribers[1](newValue);
    }
  },
  updateModel: (newValue: number) => {
    itemPageModel.tabSelection = newValue;
    itemPageController.updateView(newValue);
  },
  subscribeView: (name, stateFun) => {
    itemPageController.subscribers.push([name, stateFun]);
  },
  unsubscribeView: (name) => {
    const indexToRemove = itemPageController.subscribers.findIndex((entry) => {
      entry[0] === name;
    });
    if (indexToRemove > -1)
      itemPageController.subscribers.splice(indexToRemove, 1);
  },
};
export default itemPageController;
