import {
  Backdrop,
  Box,
  Button,
  CircularProgress,
  Container,
} from "@mui/material"
import { createElement, FC, Reducer, useEffect, useReducer } from "react"
import GenericEditController from "./genericEditController"

interface GenericEditPageProps<T extends Id> {
  item: T
  toView: () => void
  editController: GenericEditController<T>
  reducer: Reducer<any, any>
  textFieldGroup: FC<any>
}

function GenericEditPage<T extends Id>({
  item,
  toView,
  editController,
  reducer,
  textFieldGroup,
}: GenericEditPageProps<T>) {
  const [state, dispatch] = useReducer(reducer, {
    processing: false,
    errField: null,
    ...item,
  })

  useEffect(() => {
    editController.subscribedPageDispatch = dispatch
    return () => {
      editController.subscribedPageDispatch = undefined
    }
  }, [editController, dispatch])

  return (
    <form onSubmit={editController.submitHandler}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-evenly",
          minHeight: "40%",
        }}
      >
        {createElement(textFieldGroup, {
          state: state,
          errField: state.errField,
          readonly: false,
          showId: true,
          idreadonly: true,
          dispatch: dispatch,
          requirements: true,
        })}
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-evenly",
            gap: "10px",
          }}
        >
          <Button sx={{ flex: "1 1 auto" }} variant="contained" type="submit">
            Submit
          </Button>
          <Button sx={{ flex: "1 1 auto" }} variant="outlined" disabled>
            Reset
          </Button>
          <Button
            sx={{ flex: "1 1 auto" }}
            variant="outlined"
            onClick={() => toView()}
          >
            Cancel
          </Button>
        </Box>
        <Backdrop open={state.processing}>
          <CircularProgress />
        </Backdrop>
      </Container>
    </form>
  )
}

export default GenericEditPage
