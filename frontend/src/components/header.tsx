import { Person } from "@mui/icons-material";
import {
  Avatar,
  Box,
  Divider,
  Drawer,
  List,
  ListSubheader,
  Menu,
  MenuItem,
} from "@mui/material";
import { MouseEvent, useState } from "react";
import { themes } from "../styles";
import { themeObserver } from "../controllers/themeObserver";
import { pageSelectorController } from "../pageSelection/pageSelectorController";

export function SiteHeader({ siteName }: any) {
  const [headerMenuAnchor, setAnchor] = useState(null);
  const menuOpened = Boolean(headerMenuAnchor);
  const [drawerOpened, setDrawerOpened] = useState(false);

  const handleMenuOpen = (e: MouseEvent) => {
    setAnchor(e.currentTarget);
  };
  const handleMenuClose = () => {
    setAnchor(null);
  };
  const handleThemeChange = (
    e: MouseEvent<HTMLLIElement, globalThis.MouseEvent>,
    index: number
  ) => {
    themeObserver.updateModel(index);
  };

  const themesList = themes.map((theme, index) => {
    return (
      <MenuItem
        selected={index === themeObserver.currentTheme}
        onClick={(event) => handleThemeChange(event, index)}
      >
        {String(theme[0])}
      </MenuItem>
    );
  });

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
      <>
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
          <MenuItem onClick={() => {pageSelectorController.updateModel(10)}}>Login</MenuItem>
          <MenuItem onClick={() => setDrawerOpened(true)}>Settings</MenuItem>
        </Menu>
        <Drawer
          open={drawerOpened}
          onClick={() => {
            setDrawerOpened(false);
          }}
        >
          <ListSubheader>Themes</ListSubheader>
          <List>{themesList}</List>
          <Divider />
        </Drawer>
      </>
    </Box>
  );
}
