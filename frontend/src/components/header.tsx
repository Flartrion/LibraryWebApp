import { Person } from "@mui/icons-material";
import { Avatar, Box, Divider, Menu, MenuItem, Tooltip } from "@mui/material";
import { MouseEvent, useState } from "react";

export function SiteHeader({ siteName }: any) {
  const [headerMenuAnchor, setAnchor] = useState(null);
  const menuOpened = Boolean(headerMenuAnchor);
  const handleMenuOpen = (e: MouseEvent) => {
    setAnchor(e.currentTarget);
  };
  const handleMenuClose = (e) => {
    setAnchor(null);
  };
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
      <Box>
        <Avatar
          id="profile-icon"
          sx={{ cursor: "pointer" }}
          onClick={handleMenuOpen}
        >
          <Person></Person>
        </Avatar>
        <Menu
          id="profile-menu"
          anchorEl={headerMenuAnchor}
          open={menuOpened}
          onClick={handleMenuClose}
          onClose={handleMenuClose}
        >
          <MenuItem>Hoho, you're approaching me?</MenuItem>
          <Divider />
          <MenuItem>Login</MenuItem>
          <MenuItem>Settings</MenuItem>
        </Menu>
      </Box>
    </Box>
  );
}
