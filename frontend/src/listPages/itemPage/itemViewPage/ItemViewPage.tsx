import { Alert, Box, Button, Container } from "@mui/material"
import {
  lazy,
  Suspense,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react"
import userDataModel from "../../../support/userDataModel"
import itemDeleteController from "./itemDeleteDialog/itemDeleteController"
import ItemTextFieldsAbstract from "../support/itemTextFieldsAbstract"
import DefaultFallback from "../../../support/fallbacks/defaultFallback"
import Item from "../../../dataclasses/item"
import itemEditController from "./itemEditPage/itemEditController"
import editReducer from "./itemEditPage/reducer"
import BackdropFallback from "../../../support/fallbacks/backdropFallback"
import ItemViewPageEnum from "../support/itemViewPageEnum"

const ItemBalanceView = lazy(() => import("./itemBalanceView/itemBalanceView"))
const GenericDeleteDialog = lazy(
  () => import("../../../components/viewPage/deleteDialog/genericDeleteDialog")
)
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
)

interface ItemViewPageProps {
  item: Item
}

function ItemViewPage({ item }: ItemViewPageProps) {
  const [page, setPage] = useState(ItemViewPageEnum.view)
  const [deleteOpen, setDeleteOpen] = useState(false)
  const deleteFirstOpen = useRef(null)

  function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
    deleteFirstOpen.current = true
    setDeleteOpen(true)
  }

  function handleEdit(e: React.MouseEvent<HTMLButtonElement>) {
    setPage(ItemViewPageEnum.edit)
  }

  function handleViewBalance(e: React.MouseEvent<HTMLButtonElement>) {
    setPage(ItemViewPageEnum.viewBalance)
  }

  function handleDeleteCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false)
  }

  function handleDeleteSuccess() {
    setDeleteOpen(false)
  }

  useEffect(() => {
    itemDeleteController.onSuccessAction = handleDeleteSuccess
    return () => {
      itemDeleteController.onSuccessAction = undefined
    }
  })

  const toView = useCallback(() => setPage(ItemViewPageEnum.view), [item])

  let retval
  switch (page) {
    case ItemViewPageEnum.view:
      retval = (
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
              gap: "10px",
            }}
          >
            {userDataModel.userRole <= 5 && (
              <>
                <Button
                  sx={{ flex: 1 }}
                  variant="contained"
                  onClick={handleEdit}
                >
                  Edit
                </Button>
                <Button
                  sx={{ flex: 1 }}
                  variant="contained"
                  onClick={handleDelete}
                >
                  Delete
                </Button>
                <Button
                  sx={{ flex: 1 }}
                  variant="contained"
                  onClick={handleViewBalance}
                >
                  View Balance
                </Button>
              </>
            )}
            {/* TODO: Functionality */}
            {userDataModel.userRole != undefined && (
              <Button sx={{ flex: 1 }} variant="contained">
                Book rent
              </Button>
            )}
            {deleteFirstOpen.current && (
              <Suspense fallback={<BackdropFallback />}>
                <GenericDeleteDialog
                  deleteController={itemDeleteController}
                  id={item.id}
                  open={deleteOpen}
                  onCancel={handleDeleteCancel}
                />
              </Suspense>
            )}
          </Box>
        </Container>
      )
      break
    case ItemViewPageEnum.edit:
      retval = (
        <Suspense fallback={<DefaultFallback />}>
          <GenericEditPage
            editController={itemEditController}
            reducer={editReducer}
            item={item}
            toView={toView}
            textFieldGroup={ItemTextFieldsAbstract}
          />
        </Suspense>
      )
      break
    case ItemViewPageEnum.viewBalance:
      retval = (
        <Suspense fallback={<DefaultFallback />}>
          <ItemBalanceView id={item.id} toView={toView} />
        </Suspense>
      )
      break
  }

  return retval
}

export default ItemViewPage
