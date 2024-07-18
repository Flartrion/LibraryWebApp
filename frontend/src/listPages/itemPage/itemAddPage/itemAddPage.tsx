import { Button, Container, TextField } from "@mui/material";
import { useEffect, useReducer, useState } from "react";
import itemAddModel from "./itemAddModel";

const itemAddPageReducer: React.Reducer<any, any> = (
  state: any,
  action: any
) => {
  const [actionType, payload] = action;
  const newState = { ...state };
  switch (actionType) {
    case "isbn":
      newState.isbn = payload;
      itemAddModel.isbn = payload;
      return newState;
    case "rlbc":
      newState.rlbc = payload;
      itemAddModel.rlbc = payload;
      return newState;
    case "title":
      newState.title = payload;
      itemAddModel.title = payload;
      return newState;
    case "authors":
      newState.authors = payload;
      itemAddModel.authors = payload;
      return newState;
    case "type":
      newState.type = payload;
      itemAddModel.type = payload;
      return newState;
    case "language":
      newState.language = payload;
      itemAddModel.language = payload;
      return newState;
    case "details":
      newState.details = payload;
      itemAddModel.details = payload;
      return newState;
    case "processing":
      newState.processing = !newState.processing;
      break;
  }
};

function ItemAddPage() {
  const [itemAddPageState, dispatch] = useReducer(itemAddPageReducer, {
    processing: false,
    ...itemAddModel,
  });

  useEffect(() => {
    return () => {};
  });

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    data.forEach((value, key, parent) => {
      console.log(key + ": " + value);
    });
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
        <TextField
          label="ISBN"
          name="isbn"
          variant="standard"
          value={itemAddPageState.isbn}
          onChange={(event) => dispatch(["isbn", event.currentTarget.value])}
        />
        <TextField
          label="RLBC"
          name="rlbc"
          variant="standard"
          value={itemAddPageState.rlbc}
          onChange={(event) => dispatch(["rlbc", event.currentTarget.value])}
        />
        <TextField
          label="Title"
          name="title"
          variant="standard"
          required
          value={itemAddPageState.title}
          onChange={(event) => dispatch(["title", event.target.value])}
        />
        <TextField
          label="Authors"
          name="authors"
          variant="standard"
          value={itemAddPageState.authors}
          onChange={(event) => dispatch(["authors", event.target.value])}
        />
        <TextField
          label="Type"
          name="type"
          variant="standard"
          value={itemAddPageState.type}
          onChange={(event) => dispatch(["type", event.target.value])}
        />
        <TextField
          label="Language"
          name="language"
          variant="standard"
          value={itemAddPageState.language}
          onChange={(event) => dispatch(["language", event.target.value])}
        />
        <TextField
          multiline
          aria-multiline
          label="Details"
          name="details"
          variant="standard"
          value={itemAddPageState.details}
          onChange={(event) => dispatch(["details", event.target.value])}
        />
        <Button type="submit" variant="contained">
          Submit
        </Button>
      </Container>
    </form>
  );
}

export default ItemAddPage;
