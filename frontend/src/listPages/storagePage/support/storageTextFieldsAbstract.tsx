import { TextField } from "@mui/material"
import StorageActionEnum from "./storageActionEnum"
interface StorageTextFieldsProps {
  state?: any
  dispatch?: React.Dispatch<any>
  errField?: StorageActionEnum
  readonly?: boolean
  idreadonly?: boolean
  showId?: boolean
  requirements?: boolean
}

function StorageTextFieldsAbstract({
  state = undefined,
  dispatch = undefined,
  errField = undefined,
  readonly = false,
  idreadonly = true,
  showId = true,
  requirements = false,
}: StorageTextFieldsProps) {
  return (
    <>
      {showId ? (
        <TextField
          label="ID"
          name="id"
          variant="standard"
          margin="normal"
          inputProps={{
            readOnly: idreadonly,
            maxLength: 36,
          }}
          value={state != undefined ? state.id : ""}
          error={errField == StorageActionEnum.id}
          helperText={errField == StorageActionEnum.id ? "Too long!" : ""}
          onChange={(event) =>
            dispatch([StorageActionEnum.id, event.currentTarget.value])
          }
        />
      ) : null}

      <TextField
        label="Address"
        name="address"
        autoComplete="shipping street-address"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
          maxLength: 50,
        }}
        required={requirements}
        value={state.address}
        error={errField == StorageActionEnum.address}
        helperText={errField == StorageActionEnum.address ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([StorageActionEnum.address, event.currentTarget.value])
        }
      />
    </>
  )
}

export default StorageTextFieldsAbstract
