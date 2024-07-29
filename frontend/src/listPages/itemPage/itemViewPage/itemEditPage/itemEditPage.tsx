import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
} from "@mui/material";
import { useEffect, useReducer } from "react";
import reducer from "./reducer";
import Item from "../../../../dataclasses/item";
import itemEditController from "./itemEditController";
import ItemTextFieldsAbstract from "../../support/itemTextFieldsAbstract";

interface ItemEditPageProps {
  item: Item;
  setEditState: React.Dispatch<React.SetStateAction<Boolean>>;
}

function ItemEditPage({ item, setEditState }: ItemEditPageProps) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: undefined,
    ...item,
  });

  useEffect(() => {
    itemEditController.subscribedPageDispatch = dispatch;
    itemEditController.setEditState = setEditState;
    return () => {
      itemEditController.subscribedPageDispatch = undefined;
      itemEditController.setEditState = undefined;
    };
  });

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    itemEditController.submit(data);
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
          showId={true}
          idreadonly={true}
          dispatch={dispatch}
          state={state}
          requirements={true}
        />
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-evenly",
          }}
        >
          <Button type="submit" variant="contained">
            Submit
          </Button>
          <Button disabled>Reset</Button>
          <Button onClick={() => setEditState(false)}>Cancel</Button>
        </Box>
        <Backdrop open={state.processing}>
          <CircularProgress />
        </Backdrop>
      </Container>
    </form>
  );
}

export default ItemEditPage;
