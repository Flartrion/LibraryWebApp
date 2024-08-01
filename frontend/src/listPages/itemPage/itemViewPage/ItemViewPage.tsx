import { Box, Button, Container } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import userDataModel from "../../../support/userDataModel";
import ItemDeleteDialog from "./itemDeleteDialog/itemDeleteDialog";
import itemDeleteController from "./itemDeleteDialog/itemDeleteController";
import ItemTextFieldsAbstract from "../support/itemTextFieldsAbstract";
import DefaultPageSuspence from "../../../support/defaultPageSuspence";
import Item from "../../../dataclasses/item";
import itemEditController from "./itemEditPage/itemEditController";
import editReducer from "./itemEditPage/reducer";
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
);

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
      <ItemTextFieldsAbstract
        errField={undefined}
        readonly={true}
        showId={true}
        idreadonly={true}
        state={item}
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
        ) : null}
        <ItemDeleteDialog
          item={item}
          open={deleteOpen}
          onCancel={handleDeleteCancel}
        />
      </Box>
    </Container>
  ) : (
    <Suspense fallback={<DefaultPageSuspence />}>
      <GenericEditPage
        editController={itemEditController}
        reducer={editReducer}
        item={item}
        setEditState={setEditState}
        textFieldGroup={ItemTextFieldsAbstract}
      />
    </Suspense>
  );
}

export default ItemViewPage;
