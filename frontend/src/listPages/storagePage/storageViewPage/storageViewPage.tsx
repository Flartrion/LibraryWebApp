import { Box, Button, Container } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import userDataModel from "../../../support/userDataModel";

import DefaultPageSuspence from "../../../support/defaultPageSuspence";
import GenericDeleteDialog from "../../../components/viewPage/deleteDialog/genericDeleteDialog";
import Storage from "../../../dataclasses/storage";
import storageDeleteController from "./storageDeleteDialog/storageDeleteController";
import StorageTextFieldsAbstract from "../support/storageTextFieldsAbstract";
import storageEditController from "./storageEditPage/storageEditController";
import storageEditReducer from "./storageEditPage/storageEditReducer";
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
);

interface StorageViewPageProps {
  storage: Storage;
}

function StorageViewPage({ storage: user }: StorageViewPageProps) {
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
  function handleDeleteSuccess() {
    setDeleteOpen(false);
  }

  useEffect(() => {
    storageDeleteController.onSuccessAction = handleDeleteSuccess;
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
      <StorageTextFieldsAbstract
        errField={undefined}
        readonly={true}
        showId={true}
        idreadonly={true}
        state={user}
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
        <GenericDeleteDialog
          deleteController={storageDeleteController}
          item={user}
          open={deleteOpen}
          onCancel={handleDeleteCancel}
        />
      </Box>
    </Container>
  ) : (
    <Suspense fallback={<DefaultPageSuspence />}>
      <GenericEditPage
        item={user}
        editController={storageEditController}
        reducer={storageEditReducer}
        setEditState={setEditState}
        textFieldGroup={StorageTextFieldsAbstract}
      />
    </Suspense>
  );
}

export default StorageViewPage;
