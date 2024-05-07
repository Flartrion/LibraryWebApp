import {
  Avatar,
  Divider,
  Drawer,
  List,
  ListSubheader,
  Menu,
  MenuItem,
} from "@mui/material";
import themeObserver from "../theming/themeObserver";
import projectThemesArray from "../styles";
import { Person } from "@mui/icons-material";
import pageSelectorController from "../pageSelection/pageSelectorController";
import { useState } from "react";
import Page from "../pageSelection/pageSelectionEnum";
import cookieWorker from "../support/cookieWorker";

function HeaderMenu() {
  const [headerMenuAnchor, setAnchor] = useState(null);
  const menuOpened = Boolean(headerMenuAnchor);
  const [drawerOpened, setDrawerOpened] = useState(false);

  const handleMenuOpen = (e: React.MouseEvent) => {
    setAnchor(e.currentTarget);
  };
  const handleMenuClose = () => {
    setAnchor(null);
  };
  const handleThemeChange = (e: any, index: number) => {
    document.cookie = "userTheme=" + index;
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
    <>
      <Avatar
        id="profile-icon"
        sx={{ cursor: "pointer" }}
        onClick={handleMenuOpen}
      >
        <Person />
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
        {cookieWorker.extractCookie("userName") == null ? (
          <MenuItem
            onClick={() => {
              pageSelectorController.updateModel(Page.Login);
            }}
          >
            Login
          </MenuItem>
        ) : (
          <MenuItem disabled={true}>Logged in!</MenuItem>
        )}
        <MenuItem onClick={() => setDrawerOpened(true)}>Settings</MenuItem>
      </Menu>
      <Drawer
        anchor="right"
        open={drawerOpened}
        onClick={(event) => {
          event.stopPropagation();
          setDrawerOpened(false);
        }}
      >
        <ListSubheader>Themes</ListSubheader>
        <List>{themesList}</List>
        <Divider />
      </Drawer>
    </>
  );
}
export default HeaderMenu;
