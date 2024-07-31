import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
} from "@mui/material";
import { useEffect, useReducer } from "react";
import reducer from "./reducer";
import itemEditController from "./itemEditController";
import ItemTextFieldsAbstract from "../../support/itemTextFieldsAbstract";
import Item from "../../../../dataclasses/item";

interface ItemEditPageProps {
  item: Item;
  setEditState: React.Dispatch<React.SetStateAction<Boolean>>;
}

function ItemEditPage({ item, setEditState }: ItemEditPageProps) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: null,
    ...item,
  });

  useEffect(() => {
    itemEditController.subscribedPageDispatch = dispatch;
    return () => {
      itemEditController.subscribedPageDispatch = undefined;
    };
  }, [itemEditController, dispatch]);

  return (
    <form onSubmit={itemEditController.submitHandler}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-evenly",
          minHeight: "40%",
        }}
      >
        <ItemTextFieldsAbstract
          state={state}
          errField={state.errField}
          readonly={false}
          showId={true}
          idreadonly={true}
          dispatch={dispatch}
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
