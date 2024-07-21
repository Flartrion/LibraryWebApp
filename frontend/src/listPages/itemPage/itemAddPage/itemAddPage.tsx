import {
  Backdrop,
  Button,
  CircularProgress,
  Container,
  TextField,
} from "@mui/material";
import { useEffect, useReducer } from "react";
import itemAddModel from "./itemAddModel";
import itemAddPageReducer from "./itemAddPageReducer";
import itemAddController from "./itemAddController";

function ItemAddPage() {
  const [state, dispatch] = useReducer(itemAddPageReducer, {
    processing: false,
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
        <TextField
          label="ISBN"
          name="isbn"
          variant="standard"
          value={state.isbn}
          onChange={(event) => dispatch(["isbn", event.currentTarget.value])}
        />
        <TextField
          label="RLBC"
          name="rlbc"
          variant="standard"
          value={state.rlbc}
          onChange={(event) => dispatch(["rlbc", event.currentTarget.value])}
        />
        <TextField
          label="Title"
          name="title"
          variant="standard"
          required
          value={state.title}
          onChange={(event) => dispatch(["title", event.target.value])}
        />
        <TextField
          label="Authors"
          name="authors"
          variant="standard"
          value={state.authors}
          onChange={(event) => dispatch(["authors", event.target.value])}
        />
        <TextField
          label="Type"
          name="type"
          variant="standard"
          value={state.type}
          onChange={(event) => dispatch(["type", event.target.value])}
        />
        <TextField
          label="Language"
          name="language"
          variant="standard"
          value={state.language}
          onChange={(event) => dispatch(["language", event.target.value])}
        />
        <TextField
          multiline
          aria-multiline
          label="Details"
          name="details"
          variant="standard"
          value={state.details}
          onChange={(event) => dispatch(["details", event.target.value])}
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
