import { Box } from "@mui/material";

export function SiteHeader({ siteName }: any) {
  return (
    <Box css={{
        paddingLeft: "50px",
        alignItems: "center",
        minHeight: "50px",
        maxHeight: "20%"
    }}>
      <h1>{siteName}</h1>
    </Box>
  );
}
