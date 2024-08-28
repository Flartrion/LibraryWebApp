import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material"
import GenericDeleteController from "./genericDeleteController"

interface GenericDeleteDialogProps<T extends Id> {
  id: string
  deleteController: GenericDeleteController<T>
  open: boolean
  onCancel: ({}: any) => any
}

function GenericDeleteDialog<T extends Id>({
  id,
  deleteController,
  open,
  onCancel = () => {},
}: GenericDeleteDialogProps<T>) {
  function handleConfirm() {
    deleteController.delete(id)
  }

  return (
    <Dialog open={open}>
      <DialogTitle>Really delete item {id}?</DialogTitle>
      <DialogActions>
        <Button autoFocus variant="contained" onClick={onCancel}>
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
