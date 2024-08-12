import { createElement, useReducer } from "react"
import {
  Button,
  Container,
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
} from "@mui/material"
import GenericListController from "../listPage/genericListController"

interface GenericFilterPageProps<T extends Id> {
  attachedModel: GenericFieldModel<T>
  listController: GenericListController<T>
  reducer: React.Reducer<any, any>
  textFieldGroup: React.FC<any>
}

function GenericFilterPage<T extends Id>({
  attachedModel,
  listController,
  reducer,
  textFieldGroup,
}: GenericFilterPageProps<T>) {
  const [state, dispatch] = useReducer(reducer, {
    ...attachedModel,
    errField: null,
    processing: false,
  })

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    const data = new FormData(e.currentTarget)
    e.preventDefault()
    const submitType = data.get("selectedFilter")
    data.delete("selectedFilter")
    // console.log(submitType);
    // data.forEach((value, key, parent) => {
    //   console.log(key + ": " + value);
    // });
    if (submitType == "general") {
      listController.getFiltered(Object.fromEntries(data) as T)
    } else if (submitType == "id") {
      listController.getFilteredID(data.get("id").toString())
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <Container
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "space-evenly",
          minHeight: "fit-content",
          maxHeight: "fit-content(120)",
        }}
      >
        {createElement(textFieldGroup, {
          state: state,
          errField: state.errField,
          readonly: false,
          showId: true,
          idreadonly: false,
          dispatch: dispatch,
          requirements: false,
        })}
        <FormControl>
          <FormLabel id="form_radio_label">Search type</FormLabel>
          <RadioGroup
            aria-labelledby="form_radio_label"
            name="selectedFilter"
            defaultValue={"general"}
          >
            <FormControlLabel
              value="general"
              control={<Radio />}
              label="General search"
            />
            <FormControlLabel
              value="id"
              control={<Radio />}
              label="Search by ID"
            />
          </RadioGroup>
        </FormControl>
        <Button
          type="submit"
          name="submitButton"
          value="general"
          variant="contained"
        >
          Filter
        </Button>
      </Container>
    </form>
  )
}

export default GenericFilterPage
