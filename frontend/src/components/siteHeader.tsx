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
import projectThemesArray from "../styles";
import pageSelectorController from "../pageSelection/pageSelectorController";
import Page from "../pageSelection/pageSelectionEnum";
import themeObserver from "../theming/themeObserver";

function SiteHeader({ siteName }: any) {
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

  const themesList = projectThemesArray.map((theme, index) => {
    return (
      <MenuItem
        selected={index === themeObserver.currentTheme}
        key={index}
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
        maxHeight: "20vh",
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
          <ListSubheader>Hoho, you're approaching me?</ListSubheader>
          <Divider />
          <MenuItem
            onClick={() => {
              pageSelectorController.updateModel(Page.Login);
            }}
          >
            Login
          </MenuItem>
          <MenuItem onClick={() => setDrawerOpened(true)}>Settings</MenuItem>
        </Menu>
        <Drawer
          anchor="right"
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

export default SiteHeader;
