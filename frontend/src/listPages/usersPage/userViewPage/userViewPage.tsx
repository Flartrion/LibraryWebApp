import { Box, Button, Container } from "@mui/material";
import { lazy, Suspense, useEffect, useState } from "react";
import userDataModel from "../../../support/userDataModel";
import userDeleteController from "./userDeleteDialog/userDeleteController";
import UserTextFieldsAbstract from "../support/userTextFieldsAbstract";
import DefaultPageSuspence from "../../../support/defaultPageSuspence";
import User from "../../../dataclasses/user";
import GenericDeleteDialog from "../../../components/viewPage/deleteDialog/genericDeleteDialog";
import userEditController from "./userEditPage/userEditController";
import editReducer from "./userEditPage/reducer";
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
);

interface UserViewPageProps {
  user: User;
}

function UserViewPage({ user }: UserViewPageProps) {
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
    userDeleteController.onSuccessAction = handleDeleteSuccess;
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
      <UserTextFieldsAbstract
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
            {/* TODO: Functionality */}
            <Button>Balance</Button>
          </>
        ) : null}
        {/* TODO: Functionality */}
        <Button>Book rent</Button>
        <GenericDeleteDialog
          deleteController={userDeleteController}
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
        editController={userEditController}
        reducer={editReducer}
        setEditState={setEditState}
        textFieldGroup={UserTextFieldsAbstract}
      />
    </Suspense>
  );
}

export default UserViewPage;
