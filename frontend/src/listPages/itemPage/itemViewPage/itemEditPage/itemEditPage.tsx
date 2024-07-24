import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
  TextField,
} from "@mui/material";
import { useEffect, useReducer } from "react";
import reducer from "./reducer";
import Item from "../../../../dataclasses/item";
import itemEditController from "./itemEditController";

interface ItemEditPageProps {
  item: Item;
  setEditState: React.Dispatch<React.SetStateAction<Boolean>>;
}

function ItemEditPage({ item, setEditState }: ItemEditPageProps) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: "",
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
        <TextField
          label="ID"
          name="id"
          variant="standard"
          margin="normal"
          inputProps={{
            readOnly: true,
          }}
          value={item.id}
        />
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
