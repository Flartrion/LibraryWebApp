import { Button, Container, TextField } from "@mui/material";
import { useEffect, useReducer, useState } from "react";
import itemAddModel from "./itemAddModel";

function itemAddPageReducer(state: any, action: any) {}

function ItemAddPage() {
  const [processing, setProcessing] = useState<Boolean>(false);
  const [itemAddPageState, dispatch] = useReducer(itemAddPageReducer, {
    ...itemAddModel,
  });
  useEffect(() => {
    return () => {};
  });
  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
      }}
    >
      <TextField label="ISBN" variant="standard" />
      <TextField label="RLBC" variant="standard" />
      <TextField label="Title" variant="standard" />
      <TextField label="Authors" variant="standard" />
      <TextField label="Type" variant="standard" />
      <TextField label="Language" variant="standard" />
      <TextField multiline aria-multiline label="Details" variant="standard" />
      <Button variant="contained">Submit</Button>
    </Container>
  );
}

export default ItemAddPage;
