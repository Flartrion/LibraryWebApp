import User from "../dataclasses/user";

export const userController = {
  getUser: (id: number): any => {
    return fetch("/api/user");
  },

  // TODO()
  getUsers: (filters: User): User[] => {
    let headers = new Headers();
    headers.append("Content-Type", "application/json");
    headers.append("Accept", "application/json");
    let options = {
      headers: headers,
      body: filters,
    };
    let results = fetch(document.URL + "/api/user");
  },
};
