import {
  Backdrop,
  Button,
  CircularProgress,
  Container,
  TextField,
} from "@mui/material";
import { useEffect, useReducer } from "react";
import itemAddModel from "./itemAddModel";
import reducer from "./reducer";
import itemAddController from "./itemAddController";
import ItemTextFieldsAbstract from "../itemTextFieldsAbstract";

function ItemAddPage() {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: "",
    ...itemAddModel,
  });

  useEffect(() => {
    itemAddController.subscribedPageDispatch = dispatch;
    return () => {
      itemAddController.subscribedPageDispatch = undefined;
    };
  });

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // data.forEach((value, key, parent) => {
    //   console.log(key + ": " + value);
    // });
    itemAddController.submit(data);
  };

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
        />
        <Button type="submit" variant="contained">
          Submit
        </Button>
        <Backdrop open={state.processing}>
          <CircularProgress />
        </Backdrop>
      </Container>
    </form>
  );
}

export default ItemAddPage;
