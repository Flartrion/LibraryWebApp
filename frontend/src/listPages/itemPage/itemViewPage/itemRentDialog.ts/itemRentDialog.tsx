import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Input,
  ListItem,
  ListItemText,
  MenuItem,
  Skeleton,
  TextField,
} from "@mui/material"
import { Cancel, Check } from "@mui/icons-material"
import { FormEvent, memo, useEffect, useState } from "react"
import availabilityLoader from "./availabilityLoader"

interface ItemRentProps {
  id: string
  open: boolean
  onCancel: (e: React.MouseEvent<any>) => void
}

const ItemRentDialog = memo(({ id, open, onCancel }: ItemRentProps) => {
  const [availability, setAvailability] = useState<Availability[]>(null)

  function handleConfirm(e: FormEvent<HTMLFormElement>) {
    e.preventDefault()
    const data = new FormData(e.currentTarget)
    data.forEach((value, key, parent) => console.log(key + ": " + value))

    // TODO: Functionality
  }

  useEffect(() => {
    availabilityLoader.setLoadedState = setAvailability
    // console.log("setStoragesLoadedState set");
    if (open) {
      // We do want whis to get reloaded every time the dialog is open, we do.
      // This is the point to user action of closing and opening
      // this dialog, after all
      availabilityLoader.loadAvailability(id)
      // console.log("starting loading");
    }
    return () => {
      availabilityLoader.setLoadedState = undefined
      // console.log("setStoragesLoadedState unset");
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
            {availability != null ? (
              <>
                <Input name="id" value="0" readOnly sx={{ display: "none" }} />
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
                  {availability.map((value, index) => (
                    <MenuItem key={value.idStorage} value={value.idStorage}>
                      <ListItemText
                        primary={index + ": " + value.address}
                        secondary={value.amount + " available"}
                      />
                    </MenuItem>
                  ))}
                </TextField>
                <Input
                  name="dateFrom"
                  value=""
                  readOnly
                  sx={{ display: "none" }}
                />
                <TextField
                  id="dateUntil"
                  type="date"
                  name="dateUntil"
                  label="Date"
                  InputLabelProps={{ shrink: true }}
                  margin="normal"
                  required
                />
                <Input
                  name="dateStatus"
                  value=""
                  readOnly
                  sx={{ display: "none" }}
                />
                <Input
                  name="status"
                  value=""
                  readOnly
                  sx={{ display: "none" }}
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
          <Button
            startIcon={<Cancel />}
            variant="contained"
            autoFocus
            onClick={onCancel}
          >
            Cancel
          </Button>
          <Button startIcon={<Check />} variant="outlined" type="submit">
            Confirm
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  )
})

export default ItemRentDialog
