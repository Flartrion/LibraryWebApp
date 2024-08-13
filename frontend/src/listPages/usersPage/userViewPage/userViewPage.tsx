import { Box, Button, Container } from "@mui/material"
import { lazy, Suspense, useEffect, useMemo, useRef, useState } from "react"
import userDataModel from "../../../support/userDataModel"
import userDeleteController from "./userDeleteDialog/userDeleteController"
import UserTextFieldsAbstract from "../support/userTextFieldsAbstract"
import DefaultFallback from "../../../support/fallbacks/defaultFallback"
import User from "../../../dataclasses/user"
import userEditController from "./userEditPage/userEditController"
import editReducer from "./userEditPage/reducer"
import BackdropFallback from "../../../support/fallbacks/backdropFallback"
const GenericDeleteDialog = lazy(
  () => import("../../../components/viewPage/deleteDialog/genericDeleteDialog")
)
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
)

interface UserViewPageProps {
  user: User
}

function UserViewPage({ user }: UserViewPageProps) {
  const [editState, setEditState] = useState(false)
  const [deleteOpen, setDeleteOpen] = useState(false)
  const deleteFirstOpen = useRef(false)

  function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
    deleteFirstOpen.current = true
    setDeleteOpen(true)
  }

  function handleEdit(e: React.MouseEvent<HTMLButtonElement>) {
    setEditState(true)
  }

  function handleDeleteCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false)
  }
  function handleDeleteSuccess() {
    setDeleteOpen(false)
  }

  const toView = useMemo(() => () => setEditState(false), [setEditState])

  useEffect(() => {
    userDeleteController.onSuccessAction = handleDeleteSuccess
    return () => {}
  })

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
          gap: "10px",
        }}
      >
        <Button
          onClick={handleEdit}
          sx={{ flex: "1 1 auto" }}
          variant="contained"
        >
          Edit
        </Button>
        <Button
          onClick={handleDelete}
          sx={{ flex: "1 1 auto" }}
          variant="outlined"
        >
          Delete
        </Button>
        {deleteFirstOpen.current ?? (
          <Suspense fallback={<BackdropFallback />}>
            <GenericDeleteDialog
              deleteController={userDeleteController}
              id={user.id}
              open={deleteOpen}
              onCancel={handleDeleteCancel}
            />
          </Suspense>
        )}
      </Box>
    </Container>
  ) : (
    <Suspense fallback={<DefaultFallback />}>
      <GenericEditPage
        item={user}
        editController={userEditController}
        reducer={editReducer}
        toView={toView}
        textFieldGroup={UserTextFieldsAbstract}
      />
    </Suspense>
  )
}

export default UserViewPage
