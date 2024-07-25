import { TextField } from "@mui/material";
import Item from "../../dataclasses/item";

interface ItemTextFieldsProps {
  state?: any;
  dispatch?: React.Dispatch<any>;
  errField?: string;
  readonly?: boolean;
  idreadonly?: boolean;
  showId?: boolean;
  requirements?: boolean;
}

function ItemTextFieldsAbstract({
  state = undefined,
  dispatch = undefined,
  errField = "",
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
          error={errField == "id"}
          helperText={errField == "id" ? "Too long!" : ""}
          onChange={(event) => dispatch(["id", event.currentTarget.value])}
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
        error={errField == "isbn"}
        helperText={errField == "isbn" ? "Too long!" : ""}
        onChange={(event) => dispatch(["isbn", event.currentTarget.value])}
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
        error={errField == "rlbc"}
        helperText={errField == "rlbc" ? "Too long!" : ""}
        onChange={(event) => dispatch(["rlbc", event.currentTarget.value])}
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
        error={errField == "title"}
        helperText={errField == "title" ? "Too long!" : ""}
        onChange={(event) => dispatch(["title", event.target.value])}
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
        error={errField == "authors"}
        helperText={errField == "authors" ? "Too long!" : ""}
        onChange={(event) => dispatch(["authors", event.target.value])}
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
        error={errField == "type"}
        helperText={errField == "type" ? "Too long!" : ""}
        onChange={(event) => dispatch(["type", event.target.value])}
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
        error={errField == "language"}
        helperText={errField == "language" ? "Too long!" : ""}
        onChange={(event) => dispatch(["language", event.target.value])}
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
        onChange={(event) => dispatch(["details", event.target.value])}
      />
    </>
  );
}

export default ItemTextFieldsAbstract;
