import { Backdrop, Button, CircularProgress, Container } from "@mui/material";
import { createElement, useEffect, useReducer } from "react";
import GenericAddController from "./genericAddController";

interface GenericAddPageProps<T extends Id> {
  attachedModel: GenericFieldModel<T>;
  addController: GenericAddController<T>;
  reducer: React.Reducer<any, any>;
  textFieldGroup: React.FC<any>;
}

/**
 * General no-dependency DB row insert form
 * @param {T} attachedModel Is supposed to have all the fields that are characteristic of input group passed to children
 * @param {GenericAddController} addController Inherited from {@link GenericAddController}
 * @param {React.Reducer<any,any>} reducer Built at {@link itemReducerFabric}
 * @param {React.ReactNode} children Pass input field group here
 */
function GenericAddPage<T extends Id>({
  attachedModel,
  addController,
  reducer,
  textFieldGroup,
}: GenericAddPageProps<T>) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: null,
    ...attachedModel,
  });

  useEffect(() => {
    addController.subscribedPageDispatch = dispatch;
    return () => {
      addController.subscribedPageDispatch = null;
    };
  }, [addController]);

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // data.forEach((value, key, parent) => {
    //   console.log(key + ": " + value);
    // });
    addController.submit(data);
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
        {createElement(textFieldGroup, {
          state: state,
          errField: state.errField,
          readonly: false,
          showId: false,
          idreadonly: false,
          dispatch: dispatch,
          requirements: true,
        })}

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

export default GenericAddPage;
