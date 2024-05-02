import { subscribe } from "diagnostics_channel";
import { stateSubscriberEntry } from "../support/stateSubscriberEntry";

type PageSelectorModel = {
  pageSelection: number;
  subscribers: stateSubscriberEntry<number>[];
};

export const pageSelectorModel: PageSelectorModel = {
  pageSelection: 0,
  subscribers: [],
};
