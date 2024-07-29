import { TextField } from "@mui/material";
import ItemActionEnum from "./itemActionEnum";

interface ItemTextFieldsProps {
  state?: any;
  dispatch?: React.Dispatch<any>;
  errField?: ItemActionEnum;
  readonly?: boolean;
  idreadonly?: boolean;
  showId?: boolean;
  requirements?: boolean;
}

function ItemTextFieldsAbstract({
  state = undefined,
  dispatch = undefined,
  errField = undefined,
  readonly = false,
  idreadonly = true,
  showId = true,
  requirements = false,
}: ItemTextFieldsProps) {
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
          }}
          value={state != undefined ? state.id : ""}
          error={errField == ItemActionEnum.id}
          helperText={errField == ItemActionEnum.id ? "Too long!" : ""}
          onChange={(event) =>
            dispatch([ItemActionEnum.id, event.currentTarget.value])
          }
        />
      ) : null}
      <TextField
        label="ISBN"
        name="isbn"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.isbn}
        error={errField == ItemActionEnum.isbn}
        helperText={ItemActionEnum.isbn ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([ItemActionEnum.isbn, event.currentTarget.value])
        }
      />
      <TextField
        label="RLBC"
        name="rlbc"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.rlbc}
        error={errField == ItemActionEnum.rlbc}
        helperText={errField == ItemActionEnum.rlbc ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([ItemActionEnum.rlbc, event.currentTarget.value])
        }
      />
      <TextField
        label="Title"
        name="title"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        required={requirements}
        value={state.title}
        error={errField == ItemActionEnum.title}
        helperText={errField == ItemActionEnum.title ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([ItemActionEnum.title, event.target.value])
        }
      />
      <TextField
        label="Authors"
        name="authors"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.authors}
        error={errField == ItemActionEnum.authors}
        helperText={errField == ItemActionEnum.authors ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([ItemActionEnum.authors, event.target.value])
        }
      />
      <TextField
        label="Type"
        name="type"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.type}
        error={errField == ItemActionEnum.type}
        helperText={errField == ItemActionEnum.type ? "Too long!" : ""}
        onChange={(event) => dispatch([ItemActionEnum.type, event.target.value])}
      />
      <TextField
        label="Language"
        name="language"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.language}
        error={errField == ItemActionEnum.language}
        helperText={errField == ItemActionEnum.language ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([ItemActionEnum.language, event.target.value])
        }
      />
      <TextField
        multiline
        label="Details"
        name="details"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.details}
        onChange={(event) =>
          dispatch([ItemActionEnum.details, event.target.value])
        }
      />
    </>
  );
}

export default ItemTextFieldsAbstract;
