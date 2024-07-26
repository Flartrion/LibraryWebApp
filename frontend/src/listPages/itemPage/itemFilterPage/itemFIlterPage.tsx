import { useReducer } from "react";
import ItemTextFieldsAbstract from "../itemTextFieldsAbstract";
import reducer from "./reducer";
import itemFilterModel from "./itemFilterModel";
import { Button, Container } from "@mui/material";
import itemListController from "../itemList/itemListController";
import Item from "../../../dataclasses/item";

function ItemFilterPage() {
  const [state, dispatch] = useReducer(reducer, {
    ...itemFilterModel,
    errField: "",
    processing: false,
  });

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    // TODO: Filtering by ID requires different form and different handling.
    // data.forEach((value, key, parent) => {
    //   console.log(key + ": " + value);
    // });
    itemListController.getFiltered(Object.fromEntries(data) as Item);
  }

  return (
    <form onSubmit={handleSubmit}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-evenly",
          minHeight: "40%",
        }}
      >
        <ItemTextFieldsAbstract
          errField={state.errField}
          readonly={false}
          showId={false}
          idreadonly={false}
          dispatch={dispatch}
          state={state}
          requirements={false}
        />
        <Button type="submit" variant="contained">
          Submit
        </Button>
      </Container>
    </form>
  );
}

export default ItemFilterPage;
