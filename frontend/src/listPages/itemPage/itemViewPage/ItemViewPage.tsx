import { Box, Button, Container, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import Item from "../../../dataclasses/item";
import userDataModel from "../../../support/userDataModel";
import ItemEditPage from "./itemEditPage/itemEditPage";
import ItemDeleteDialog from "./itemDeleteDialog/itemDeleteDialog";
import itemViewModel from "./itemViewModel";
import itemListModel from "../itemList/itemListModel";
import itemPageController from "../itemPageController";
import ItemPageTab from "../itemPageTabsEnum";
import itemDeleteController from "./itemDeleteDialog/itemDeleteController";

interface ItemViewPageProps {
  item: Item;
}

function ItemViewPage({ item }: ItemViewPageProps) {
  const [editState, setEditState] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);

  function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(true);
  }

  function handleEdit(e: React.MouseEvent<HTMLButtonElement>) {
    setEditState(true);
  }

  function handleDeleteCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false);
  }
  function handleDeleteSuccess(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false);
    itemViewModel.item = undefined;
    itemViewModel.itemIndex = undefined;
    itemListModel.itemsLoaded = false;
    itemListModel.itemSelection = -1;
    itemListModel.items = undefined;
    itemPageController.updateModel(ItemPageTab.Items);
  }

  useEffect(() => {
    itemDeleteController.onSuccessAction = handleDeleteSuccess;
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
            <Button onClick={handleEdit}>Edit</Button>
            <Button onClick={handleDelete}>Delete</Button>
          </>
        ) : (
          ""
        )}
        <ItemDeleteDialog
          item={item}
          open={deleteOpen}
          onCancel={handleDeleteCancel}
        />
      </Box>
    </Container>
  ) : (
    <ItemEditPage item={item} setEditState={setEditState} />
  );
}

export default ItemViewPage;
