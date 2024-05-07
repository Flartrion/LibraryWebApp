import User from "../../dataclasses/user";

// Only here for type-safety.
type UsersPageModel = {
  scrollOffset: number;
  userSelection: number;
  tabSelection: number;
  usersLoaded: boolean;
  users: User[];
};

const usersPageModel: UsersPageModel = {
  userSelection: -1,
  tabSelection: 0,
  scrollOffset: 0,
  usersLoaded: false,
  users: [],
};
export default usersPageModel;
