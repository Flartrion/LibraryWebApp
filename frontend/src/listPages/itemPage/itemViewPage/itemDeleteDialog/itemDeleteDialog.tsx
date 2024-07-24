import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material";
import Item from "../../../../dataclasses/item";
import itemDeleteController from "./itemDeleteController";

interface ItemDeleteDialogProps {
  item: Item;
  open: boolean;
  onCancel: ({}: any) => any;
}

function ItemDeleteDialog({
  item,
  open,
  onCancel = () => {},
}: ItemDeleteDialogProps) {
  function handleConfirm() {
    itemDeleteController.delete(item.id);
  }

  return (
    <Dialog open={open}>
      <DialogTitle>Really delete item {item.id}?</DialogTitle>
      <DialogActions>
        <Button autoFocus onClick={onCancel}>
          Cancel
        </Button>
        <Button variant="outlined" onClick={handleConfirm}>
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default ItemDeleteDialog;
