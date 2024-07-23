import { Box, Button, Container, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import Item from "../../../dataclasses/item";
import userDataModel from "../../../support/userDataModel";
import ItemEditPage from "./itemEditPage/itemEditPage";

interface ItemViewPageProps {
  item: Item;
}

function ItemViewPage({ item }: ItemViewPageProps) {
  const [editState, setEditState] = useState(false);

  useEffect(() => {
    return () => {};
  });

  return !editState ? (
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
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-evenly",
        }}
      >
        {userDataModel.userRole <= 5 ? (
          <>
            <Button onClick={() => setEditState(true)}>Edit</Button>
            <Button>Delete</Button>
          </>
        ) : (
          ""
        )}
      </Box>
    </Container>
  ) : (
    <ItemEditPage item={item} setEditState={setEditState} />
  );
}

export default ItemViewPage;
