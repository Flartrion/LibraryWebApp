import { Alert, Box, Button, Container } from "@mui/material"
import {
  lazy,
  MutableRefObject,
  Suspense,
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

const ItemBalanceChangeDialog = lazy(
  () => import("./itemBalanceDialog/itemBalanceDialog")
)
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
  const deleteFirstOpen: MutableRefObject<boolean> = useRef(null)
  const [balanceOpen, setBalanceOpen] = useState(false)
  const balanceChangeFirstOpen: MutableRefObject<boolean> = useRef(null)

  function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
    deleteFirstOpen.current = true
    setDeleteOpen(true)
  }

  function handleEdit(e: React.MouseEvent<HTMLButtonElement>) {
    setPage(ItemViewPageEnum.edit)
  }

  function handleEditBalance(e: React.MouseEvent<HTMLButtonElement>) {
    balanceChangeFirstOpen.current = true
    setBalanceOpen(true)
  }

  function handleViewBalance(e: React.MouseEvent<HTMLButtonElement>) {
    setPage(ItemViewPageEnum.viewBalance)
  }

  function handleDeleteCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false)
  }
  function handleBalanceCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setBalanceOpen(false)
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

  const toView = useMemo(() => () => setPage(ItemViewPageEnum.view), [setPage])

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
                  onClick={handleEditBalance}
                >
                  Edit Balance
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
                  item={item}
                  open={deleteOpen}
                  onCancel={handleDeleteCancel}
                />
              </Suspense>
            )}
            {balanceChangeFirstOpen.current && (
              <Suspense fallback={<BackdropFallback />}>
                <ItemBalanceChangeDialog
                  item={item}
                  onCancel={handleBalanceCancel}
                  open={balanceOpen}
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
        <Container>
          <Alert variant="filled">W.I.P.</Alert>
          <Button onClick={() => toView()}>Return</Button>
        </Container>
      )
      break
  }

  return retval
}

export default ItemViewPage
