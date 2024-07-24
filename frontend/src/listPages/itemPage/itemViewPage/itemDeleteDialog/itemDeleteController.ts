import { UnfoldLess } from "@mui/icons-material";

const itemDeleteController = {
  onSuccessAction: (e: React.MouseEvent<HTMLButtonElement>) => {},
  delete(id: string) {
    const options = {
      method: "DELETE",
    };
    let responseStatus: number;
    const request = new Request("items/" + id, options);
    fetch(request)
      .then((response) => {
        responseStatus = response.status;
        return response.text();
      })
      .then((body) => {
        console.log(body);
      })
      .catch((reason) => {
        console.log(reason);
      })
      .finally(() => {
        if (responseStatus == 200) this.onSuccessAction();
      });
  },
};

export default itemDeleteController;
