import * as React from 'react';
import { Outlet, useNavigate } from "react-router-dom";
import { AppBar, Box, Toolbar, IconButton, Typography, Menu, Container, MenuItem, useTheme } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';

const pages = ['Sign In', 'Sign Up'];

const HomeAppBar = ({ handleResetRoles }) => {
  const theme = useTheme()
  const navigate = useNavigate()
  const [anchorElNav, setAnchorElNav] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleRoute = (page) => {
    navigate(`${page.toLowerCase().replace(' ', '')}`)
  }

  return (
    <div>
      <AppBar position="static" sx={{ bgcolor: theme.palette.background.paper }}>
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <IconButton disableRipple onClick={() => handleRoute('')}>
              <img src="/SSSLogo1.png" width="70px" />
            </IconButton>
            <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="inherit"
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: 'bottom',
                  horizontal: 'left',
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left',
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={{
                  display: { xs: 'block', md: 'none' },
                }}
              >
                {pages.map((page) => (
                  <MenuItem key={page} onClick={() => handleRoute(page)}>
                    <Typography textAlign="center">{page}</Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
            <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
              {pages.map((page) => (
                <MenuItem key={page} onClick={() => handleRoute(page)}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </div>
  );
};
export default HomeAppBar;
