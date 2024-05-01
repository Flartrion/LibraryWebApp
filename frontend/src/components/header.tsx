import { Person } from "@mui/icons-material";
import { Avatar, Box } from "@mui/material";

export function SiteHeader({ siteName }: any) {
  return (
    <Box
      css={{
        paddingLeft: "50px",
        paddingRight: "30px",
        alignItems: "center",
        minHeight: "50px",
        maxHeight: "20%",
      }}
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <h1>{siteName}</h1>
      <Avatar>
        <Person />
      </Avatar>
    </Box>
  );
}
