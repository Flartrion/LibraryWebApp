import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material"
import GenericDeleteController from "./genericDeleteController"

interface GenericDeleteDialogProps<T extends Id> {
  item: T
  deleteController: GenericDeleteController<T>
  open: boolean
  onCancel: ({}: any) => any
}

function GenericDeleteDialog<T extends Id>({
  item,
  deleteController,
  open,
  onCancel = () => {},
}: GenericDeleteDialogProps<T>) {
  function handleConfirm() {
    deleteController.delete(item.id)
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
  )
}

export default GenericDeleteDialog
