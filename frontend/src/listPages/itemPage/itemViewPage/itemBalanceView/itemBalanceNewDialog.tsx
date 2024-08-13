import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Input,
  MenuItem,
  Skeleton,
  TextField,
} from "@mui/material"
import { Cancel, Check } from "@mui/icons-material"
import { FormEvent, memo, useEffect, useState } from "react"
import itemBalanceController from "./itemBalanceController"
import itemBalanceModel from "./itemBalanceModel"
import Storage from "../../../../dataclasses/storage"

interface ItemBalanceProps {
  id: string
  open: boolean
  onCancel: () => void
}

const ItemBalanceNewDialog = memo(
  ({ id, open, onCancel }: ItemBalanceProps) => {
    function handleConfirm(e: FormEvent<HTMLFormElement>) {
      e.preventDefault()
      const data = new FormData(e.currentTarget)
      // data.forEach((value, key, parent) => console.log(key + ": " + value));
      itemBalanceController.newBalanceEntry(data)
      // TODO: Functionality
    }
    const [storagesLoaded, setStoragesLoaded] = useState(
      itemBalanceModel.storagesLoaded
    )

    useEffect(() => {
      itemBalanceController.dialogOnCancel = onCancel
      itemBalanceController.dialogSetStoragesLoadedState = setStoragesLoaded
      // console.log("setStoragesLoadedState set");
      if (open) {
        // console.log("starting loading");
        itemBalanceController.loadStorages()
        // As effect is executed on "open" prop change, that should make it only
        // reload storages on re-opening of this dialog,
        // which is intended behaviour
      }
      return () => {
        // console.log("setStoragesLoadedState unset");
        itemBalanceController.dialogSetStoragesLoadedState = undefined
        itemBalanceController.dialogOnCancel = undefined
      }
    }, [open])

    return (
      <Dialog open={open}>
        <DialogTitle>Balance change entry for {id}:</DialogTitle>
        <form onSubmit={handleConfirm}>
          <DialogContent>
            <Container
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignContent: "space-around",
              }}
            >
              {storagesLoaded ? (
                <>
                  <Input
                    name="id"
                    value="0"
                    readOnly
                    sx={{ display: "none" }}
                  />
                  <TextField
                    label="Item ID"
                    name="idItem"
                    value={id}
                    inputProps={{ readOnly: true }}
                    margin="normal"
                  />
                  <TextField
                    select
                    id="storageSelectionTextfield"
                    name="idStorage"
                    label="Storage"
                    SelectProps={{ autoWidth: true }}
                    margin="normal"
                    required
                  >
                    {itemBalanceModel.storages.map((value, index) => (
                      <MenuItem key={value.id} value={value.id}>
                        {index + ": " + value.address}
                      </MenuItem>
                    ))}
                  </TextField>
                  <TextField
                    id="balanceChangeTextfield"
                    inputMode="numeric"
                    name="change"
                    label="Balance change"
                    margin="normal"
                    required
                  />
                  <TextField
                    id="balanceChangeDate"
                    type="date"
                    name="date"
                    label="Date"
                    InputLabelProps={{ shrink: true }}
                    margin="normal"
                    required
                  />
                </>
              ) : (
                <>
                  <Skeleton variant="rounded" width="50%" height="50px" />
                  <Skeleton variant="rounded" width="50%" height="50px" />
                  <Skeleton variant="rounded" width="50%" height="50px" />
                  <Skeleton variant="rounded" width="50%" height="50px" />
                </>
              )}
            </Container>
          </DialogContent>
          <DialogActions>
            <Button startIcon={<Cancel />} autoFocus onClick={onCancel}>
              Cancel
            </Button>
            <Button startIcon={<Check />} variant="outlined" type="submit">
              Confirm
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    )
  }
)

export default ItemBalanceNewDialog
