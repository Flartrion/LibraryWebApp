import { request } from "https";
import { json } from "node:stream/consumers";

const UserGetController = {
  getUser: (id: number) => {
    request("https://localhost:8443/api/user/" + id.toString(), (res) =>
      console.log(res.statusCode)
    );
  },
};
