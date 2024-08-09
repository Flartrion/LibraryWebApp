import { Box, Button, Container } from "@mui/material";
import {
  lazy,
  MutableRefObject,
  Suspense,
  useEffect,
  useRef,
  useState,
} from "react";
import userDataModel from "../../../support/userDataModel";
import itemDeleteController from "./itemDeleteDialog/itemDeleteController";
import ItemTextFieldsAbstract from "../support/itemTextFieldsAbstract";
import DefaultFallback from "../../../support/fallbacks/defaultFallback";
import Item from "../../../dataclasses/item";
import itemEditController from "./itemEditPage/itemEditController";
import editReducer from "./itemEditPage/reducer";
import BackdropFallback from "../../../support/fallbacks/backdropFallback";

const ItemBalanceDialog = lazy(
  () => import("./itemBalanceDialog/itemBalanceDialog")
);
const GenericDeleteDialog = lazy(
  () => import("../../../components/viewPage/deleteDialog/genericDeleteDialog")
);
const GenericEditPage = lazy(
  () => import("../../../components/viewPage/editPage/genericEditPage")
);

interface ItemViewPageProps {
  item: Item;
}

function ItemViewPage({ item }: ItemViewPageProps) {
  const [editState, setEditState] = useState(false);
  const [deleteOpen, setDeleteOpen] = useState(false);
  const deleteFirstOpen: MutableRefObject<boolean> = useRef(null);
  const [balanceOpen, setBalanceOpen] = useState(false);
  const balanceFirstOpen: MutableRefObject<boolean> = useRef(null);

  function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
    deleteFirstOpen.current = true;
    setDeleteOpen(true);
  }

  function handleEdit(e: React.MouseEvent<HTMLButtonElement>) {
    setEditState(true);
  }

  function handleBalance(e: React.MouseEvent<HTMLButtonElement>) {
    balanceFirstOpen.current = true;
    setBalanceOpen(true);
  }

  function handleDeleteCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setDeleteOpen(false);
  }
  function handleBalanceCancel(e: React.MouseEvent<HTMLButtonElement>) {
    setBalanceOpen(false);
  }
  function handleDeleteSuccess() {
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
            <Button variant="contained" onClick={handleEdit}>
              Edit
            </Button>
            <Button variant="contained" onClick={handleDelete}>
              Delete
            </Button>
            <Button variant="contained" onClick={handleBalance}>
              Balance
            </Button>
          </>
        ) : null}
        {/* TODO: Functionality */}
        <Button variant="contained">Book rent</Button>
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
        {balanceFirstOpen.current && (
          <Suspense fallback={<BackdropFallback />}>
            <ItemBalanceDialog
              item={item}
              onCancel={handleBalanceCancel}
              open={balanceOpen}
            />
          </Suspense>
        )}
      </Box>
    </Container>
  ) : (
    <Suspense fallback={<DefaultFallback />}>
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
