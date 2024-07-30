import { Backdrop, Button, CircularProgress, Container } from "@mui/material";
import React, { useEffect, useReducer } from "react";
import AbstractAddController from "./abstractAddController";

interface AbstractAddPageProps {
  attachedModel: any;
  addController: AbstractAddController;
  reducer: React.Reducer<any, any>;
  children: React.ReactNode;
}

/**
 * Genreral no-dependency DB row input form
 * @param {any} attachedModel Is supposed to have all the fields that are characteristic of input group passed to children
 * @param {AbstractAddController} addController Inherited from {@link AbstractAddController}
 * @param {React.Reducer<any,any>} reducer Built at {@link itemReducerFabric}
 * @param {React.ReactNode} children Pass input field group here
 */
function AbstractAddPage({
  attachedModel,
  addController,
  reducer,
  children,
}: AbstractAddPageProps) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: undefined,
    ...attachedModel,
  });

  useEffect(() => {
    addController.subscribedPageDispatch = dispatch;
    return () => {
      addController.subscribedPageDispatch = undefined;
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
        {children}
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

export default AbstractAddPage;
