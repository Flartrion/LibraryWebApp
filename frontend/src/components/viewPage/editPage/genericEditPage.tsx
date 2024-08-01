import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
} from "@mui/material";
import { createElement, FC, Reducer, useEffect, useReducer } from "react";

interface GenericEditPageProps<T extends Id> {
  item: T;
  setEditState: React.Dispatch<React.SetStateAction<Boolean>>;
  // TODO: Replace with class
  editController: any;
  reducer: Reducer<any, any>;
  textFieldGroup: FC<any>;
}

function GenericEditPage<T extends Id>({
  item,
  setEditState,
  editController,
  reducer,
  textFieldGroup,
}: GenericEditPageProps<T>) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: null,
    ...item,
  });

  useEffect(() => {
    editController.subscribedPageDispatch = dispatch;
    return () => {
      editController.subscribedPageDispatch = undefined;
    };
  }, [editController, dispatch]);

  return (
    <form onSubmit={editController.submitHandler}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-evenly",
          minHeight: "40%",
        }}
      >
        {createElement(textFieldGroup, {
          state: state,
          errField: state.errField,
          readonly: false,
          showId: true,
          idreadonly: true,
          dispatch: dispatch,
          requirements: true,
        })}
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

export default GenericEditPage;
