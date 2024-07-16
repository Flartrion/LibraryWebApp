import { Button, Container, TextField } from "@mui/material";

function ItemAddPage() {
  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
      }}
    >
      <TextField label="isbn" variant="standard" />
      <TextField label="rlbc" variant="standard" />
      <TextField label="title" variant="standard" />
      <TextField label="authors" variant="standard" />
      <TextField label="type" variant="standard" />
      <TextField label="language" variant="standard" />
      <TextField multiline aria-multiline label="details" variant="standard" />
      <Button variant="contained">Submit</Button>
    </Container>
  );
}

export default ItemAddPage;
