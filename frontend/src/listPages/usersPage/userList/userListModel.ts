import User from "../../../dataclasses/user";

const userListModel: GenericListModel<User> = {
  items: [],
  loaded: false,
  selection: undefined,
  scrollOffset: 0,
};

export default userListModel;
