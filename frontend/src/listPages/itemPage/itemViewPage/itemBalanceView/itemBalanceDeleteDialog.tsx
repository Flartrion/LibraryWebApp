import { Button, Dialog, DialogActions, DialogTitle } from "@mui/material"
import itemBalanceController from "./itemBalanceController"
import { useEffect } from "react"

interface ItemBalanceDeleteProps {
  id: string
  open: boolean
  onCancel: () => void
}

function ItemBalanceDeleteDialog({
  id,
  open,
  onCancel = () => {},
}: ItemBalanceDeleteProps) {
  function handleConfirm() {
    itemBalanceController.deleteBalanceEntry(id)
  }

  useEffect(() => {
    itemBalanceController.deleteDialogClose = onCancel
    return () => {
      itemBalanceController.deleteDialogClose = undefined
    }
  })

  return (
    <Dialog open={open}>
      <DialogTitle>Really delete entry {id}?</DialogTitle>
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

export default ItemBalanceDeleteDialog
