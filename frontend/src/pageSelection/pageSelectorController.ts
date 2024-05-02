import ReactGeneralController from "../support/reactController";
import stateSubscriberEntry from "../support/stateSubscriberEntry";
import pageSelectorModel from "./pageSelectorModel";

type PageSelectorController = ReactGeneralController<number> & {
  subscribers: stateSubscriberEntry<number>[];
};

const pageSelectorController: PageSelectorController = {
  subscribers: [],
  updateView: (newValue: number) => {
    for (let subscribers of pageSelectorController.subscribers) {
      subscribers[1](newValue);
    }
  },
  updateModel: (newValue: number) => {
    pageSelectorModel.pageSelection = newValue;
    pageSelectorController.updateView(newValue);
  },
  subscribeView: (name, stateFun) => {
    pageSelectorController.subscribers.push([name, stateFun]);
  },
  unsubscribeView: (name) => {
    const indexToRemove = pageSelectorController.subscribers.findIndex(
      (entry) => {
        entry[0] === name;
      }
    );
    if (indexToRemove > -1)
      pageSelectorController.subscribers.splice(indexToRemove, 1);
  },
};
export default pageSelectorController;
