import { Container, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import Item from "../../../dataclasses/item";

interface ItemViewPageProps {
  item: Item;
}

function ItemViewPage({ item }: ItemViewPageProps) {
  const [editState, setEditState] = useState(false);

  useEffect(() => {
    return () => {};
  });

  return (
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
        inputProps={{
          readOnly: true,
        }}
        value={item.isbn}
      />
      <TextField
        label="RLBC"
        name="rlbc"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.rlbc}
      />
      <TextField
        label="Title"
        name="title"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.title}
      />
      <TextField
        label="Authors"
        name="authors"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.authors}
      />
      <TextField
        label="Type"
        name="type"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.type}
      />
      <TextField
        label="Language"
        name="language"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.language}
      />
      <TextField
        multiline
        aria-multiline
        label="Details"
        name="details"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: true,
        }}
        value={item.details}
      />
      {/* <Button type="submit" variant="contained">
        Submit
      </Button> */}
    </Container>
  );
}

export default ItemViewPage;
