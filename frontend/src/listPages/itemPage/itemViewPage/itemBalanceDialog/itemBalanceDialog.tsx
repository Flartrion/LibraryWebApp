import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import Item from "../../../../dataclasses/item";
import { CancelOutlined, Check } from "@mui/icons-material";

interface ItemBalanceProps {
  item: Item;
  open: boolean;
  onCancel: ({}: any) => void;
}

function ItemBalanceDialog({ item, open, onCancel }: ItemBalanceProps) {
  function handleConfirm() {
    // TODO: Functionality
  }

  return (
    <Dialog open={open}>
      <DialogTitle>Balance change entry for {item.id}:</DialogTitle>
      <DialogContent></DialogContent>
      <DialogActions>
        <Button startIcon={<CancelOutlined />} autoFocus onClick={onCancel}>
          Cancel
        </Button>
        <Button
          startIcon={<Check />}
          variant="outlined"
          onClick={handleConfirm}
        >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default ItemBalanceDialog;
