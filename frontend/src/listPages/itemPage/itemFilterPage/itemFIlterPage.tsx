import { useReducer } from "react";
import ItemTextFieldsAbstract from "../itemTextFieldsAbstract";
import reducer from "./reducer";
import itemFilterModel from "./itemFilterModel";
import {
  Button,
  Container,
  FormControl,
  FormControlLabel,
  FormLabel,
  Radio,
  RadioGroup,
} from "@mui/material";
import itemListController from "../itemList/itemListController";
import Item from "../../../dataclasses/item";

function ItemFilterPage() {
  const [state, dispatch] = useReducer(reducer, {
    ...itemFilterModel,
    errField: "",
    processing: false,
  });

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    const data = new FormData(e.currentTarget);
    e.preventDefault();
    const submitType = data.get("selectedFilter");
    data.delete("selectedFilter");
    // console.log(submitType);
    // data.forEach((value, key, parent) => {
    //   console.log(key + ": " + value);
    // });
    if (submitType == "general") {
      itemListController.getFiltered(Object.fromEntries(data) as Item);
    } else if (submitType == "id") {
      itemListController.getFilteredID(data.get("id").toString());
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
        <ItemTextFieldsAbstract
          errField={state.errField}
          readonly={false}
          showId={true}
          idreadonly={false}
          dispatch={dispatch}
          state={state}
          requirements={false}
        />
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
  );
}

export default ItemFilterPage;
