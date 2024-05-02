import { pageSelectorModel } from "./pageSelectorModel";

type PageSelectorController = {
  updateView: { (newValue: number): void };
  updateModel: { (newValue: number): void };
  subscribeView: {
    (
      name: string,
      stateFun: React.Dispatch<React.SetStateAction<number>>
    ): void;
  };
  unsubscribeView: { (name: string): void };
};

export const pageSelectorController: PageSelectorController = {
  updateView: (newValue: number) => {
    for (let subscribers of pageSelectorModel.subscribers) {
      subscribers[1](newValue);
    }
  },
  updateModel: (newValue: number) => {
    pageSelectorModel.pageSelection = newValue;
    pageSelectorController.updateView(newValue);
  },
  subscribeView: (name, stateFun) => {
    pageSelectorModel.subscribers.push([name, stateFun]);
  },
  unsubscribeView: (name) => {
    const indexToRemove = pageSelectorModel.subscribers.findIndex((entry) => {
      entry[0] === name;
    });
    if (indexToRemove > -1)
      pageSelectorModel.subscribers = pageSelectorModel.subscribers.splice(
        indexToRemove,
        1
      );
  },
};
