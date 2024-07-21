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
        <TextField
          label="ISBN"
          name="isbn"
          variant="standard"
          margin="normal"
          value={state.isbn}
          error={state.errField == "isbn"}
          helperText={state.errField == "isbn" ? "Too long!" : ""}
          onChange={(event) => dispatch(["isbn", event.currentTarget.value])}
        />
        <TextField
          label="RLBC"
          name="rlbc"
          variant="standard"
          margin="normal"
          value={state.rlbc}
          error={state.errField == "rlbc"}
          helperText={state.errField == "rlbc" ? "Too long!" : ""}
          onChange={(event) => dispatch(["rlbc", event.currentTarget.value])}
        />
        <TextField
          label="Title"
          name="title"
          variant="standard"
          margin="normal"
          required
          value={state.title}
          error={state.errField == "title"}
          helperText={state.errField == "title" ? "Too long!" : ""}
          onChange={(event) => dispatch(["title", event.target.value])}
        />
        <TextField
          label="Authors"
          name="authors"
          variant="standard"
          margin="normal"
          value={state.authors}
          error={state.errField == "authors"}
          helperText={state.errField == "authors" ? "Too long!" : ""}
          onChange={(event) => dispatch(["authors", event.target.value])}
        />
        <TextField
          label="Type"
          name="type"
          variant="standard"
          margin="normal"
          value={state.type}
          error={state.errField == "type"}
          helperText={state.errField == "type" ? "Too long!" : ""}
          onChange={(event) => dispatch(["type", event.target.value])}
        />
        <TextField
          label="Language"
          name="language"
          variant="standard"
          margin="normal"
          value={state.language}
          error={state.errField == "language"}
          helperText={state.errField == "language" ? "Too long!" : ""}
          onChange={(event) => dispatch(["language", event.target.value])}
        />
        <TextField
          multiline
          aria-multiline
          label="Details"
          name="details"
          variant="standard"
          margin="normal"
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
