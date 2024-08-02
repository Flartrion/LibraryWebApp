import { TextField } from "@mui/material";
import UserActionEnum from "./userActionEnum";

interface UserTextFieldsProps {
  state?: any;
  dispatch?: React.Dispatch<any>;
  errField?: UserActionEnum;
  readonly?: boolean;
  idreadonly?: boolean;
  showId?: boolean;
  requirements?: boolean;
}

function UserTextFieldsAbstract({
  state = undefined,
  dispatch = undefined,
  errField = undefined,
  readonly = false,
  idreadonly = true,
  showId = true,
  requirements = false,
}: UserTextFieldsProps) {
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
          error={errField == UserActionEnum.id}
          helperText={errField == UserActionEnum.id ? "Too long!" : ""}
          onChange={(event) =>
            dispatch([UserActionEnum.id, event.currentTarget.value])
          }
        />
      ) : null}
      <TextField
        label="Role code"
        name="role"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        inputMode="numeric"
        required={requirements}
        value={state.role}
        error={errField == UserActionEnum.role}
        helperText={
          errField == UserActionEnum.role ? "Keep this positive!" : ""
        }
        onChange={(event) =>
          dispatch([UserActionEnum.role, event.currentTarget.value])
        }
      />
      <TextField
        label="Full name"
        name="fullName"
        autoComplete="name"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        required={requirements}
        value={state.fullName}
        error={errField == UserActionEnum.fullName}
        helperText={errField == UserActionEnum.fullName ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([UserActionEnum.fullName, event.currentTarget.value])
        }
      />
      <TextField
        label="Date of birth"
        name="dob"
        type="date"
        variant="standard"
        margin="normal"
        inputProps={{
          readOnly: readonly,
        }}
        value={state.dob}
        error={errField == UserActionEnum.dob}
        helperText={errField == UserActionEnum.dob ? "Too young!" : ""}
        onChange={(event) => dispatch([UserActionEnum.dob, event.target.value])}
      />
      <TextField
        label="Email"
        name="title"
        variant="standard"
        margin="normal"
        inputMode="email"
        inputProps={{
          readOnly: readonly,
        }}
        required={requirements}
        value={state.email}
        error={errField == UserActionEnum.email}
        helperText={errField == UserActionEnum.email ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([UserActionEnum.email, event.target.value])
        }
      />
      <TextField
        label="Phone number"
        name="phoneNumber"
        variant="standard"
        margin="normal"
        inputMode="tel"
        inputProps={{
          readOnly: readonly,
        }}
        required={requirements}
        value={state.phoneNumber}
        error={errField == UserActionEnum.phoneNumber}
        helperText={errField == UserActionEnum.phoneNumber ? "Too long!" : ""}
        onChange={(event) =>
          dispatch([UserActionEnum.phoneNumber, event.target.value])
        }
      />
    </>
  );
}

export default UserTextFieldsAbstract;
