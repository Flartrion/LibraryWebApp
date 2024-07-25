import { useReducer } from "react";
import ItemTextFieldsAbstract from "../itemTextFieldsAbstract";
import reducer from "./reducer";
import itemFilterModel from "./itemFilterModel";
import { Button, Container } from "@mui/material";

function ItemFilterPage() {
  const [state, dispatch] = useReducer(reducer, {
    ...itemFilterModel,
    errField: "",
    processing: false,
  });

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    data.forEach((value, key, parent) => {
      console.log(key + ": " + value);
    });
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
          showId={true}
          errField={state.errField}
          readonly={false}
          idreadonly={false}
          dispatch={dispatch}
          state={state}
        />
        <Button type="submit" variant="contained">
          Submit
        </Button>
      </Container>
    </form>
  );
}

export default ItemFilterPage;
