import Storage from "../../../dataclasses/storage";

const storageListModel: GenericListModel<Storage> = {
  items: undefined,
  loaded: false,
  scrollOffset: 0,
  selection: undefined,
};

export default storageListModel;
