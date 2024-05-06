import { Box } from "@mui/material";
import HeaderMenu from "./dropoutMenu";

function SiteHeader({ siteName }: any) {
  return (
    <Box
      css={{
        paddingLeft: "50px",
        paddingRight: "30px",
        alignItems: "center",
        minHeight: "50px",
        maxHeight: "20vh",
      }}
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <h1>{siteName}</h1>
      <HeaderMenu />
    </Box>
  );
}

export default SiteHeader;
