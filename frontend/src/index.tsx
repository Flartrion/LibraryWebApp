import { Button } from "@mui/material";
import { createRoot } from "react-dom/client";

const container = document.getElementById("root");
const root = createRoot(container!);
root.render(
  <div
    css={{
      padding: "0%",
      margin: "0%",
      display: "flex",
      flexBasis: "width",
      justifySelf: "center",
      alignSelf: "start",
      alignItems: "stretch",
      width: "100%",
      length: "max-content",
      border: "solid",
      borderWidth: "4px",
      borderColor: "red",
    }}
  >
    <Button>1</Button>
    <Button>2</Button>
    <Button>3</Button>
    <Button>4</Button>
    <Button>5</Button>
  </div>
);
