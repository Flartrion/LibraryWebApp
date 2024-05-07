import User from "../../dataclasses/user";

// Only here for type-safety.
type UsersPageModel = {
  scrollOffset: number;
  itemSelection: number;
  tabSelection: number;
  usersLoaded: boolean;
  items: User[];
};

const usersPageModel: UsersPageModel = {
  itemSelection: -1,
  tabSelection: 0,
  scrollOffset: 0,
  usersLoaded: false,
  items: [],
};
export default usersPageModel;
