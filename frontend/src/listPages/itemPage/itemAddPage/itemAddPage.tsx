import { Button, Container, TextField } from "@mui/material";
import { useEffect, useReducer, useState } from "react";
import itemAddModel from "./itemAddModel";

const itemAddPageReducer: React.Reducer<any, any> = (
  state: any,
  action: any
) => {};

function ItemAddPage() {
  const [processing, setProcessing] = useState<Boolean>(false);
  const [itemAddPageState, dispatch] = useReducer(itemAddPageReducer, {
    ...itemAddModel,
  });

  useEffect(() => {
    return () => {};
  });

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
  };

  return (
    <form onSubmit={handleSubmit}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
        }}
      >
        <TextField
          label="ISBN"
          variant="standard"
          value={itemAddPageState.isbn}
        />
        <TextField
          label="RLBC"
          variant="standard"
          value={itemAddPageState.rlbc}
        />
        <TextField
          label="Title"
          variant="standard"
          required
          value={itemAddPageState.title}
        />
        <TextField
          label="Authors"
          variant="standard"
          value={itemAddPageState.authors}
        />
        <TextField
          label="Type"
          variant="standard"
          value={itemAddPageState.type}
        />
        <TextField
          label="Language"
          variant="standard"
          value={itemAddPageState.language}
        />
        <TextField
          multiline
          aria-multiline
          label="Details"
          variant="standard"
          value={itemAddPageState.details}
        />
        <Button type="submit" variant="contained">
          Submit
        </Button>
      </Container>
    </form>
  );
}

export default ItemAddPage;
