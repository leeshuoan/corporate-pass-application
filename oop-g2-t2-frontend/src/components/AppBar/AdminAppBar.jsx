import * as React from 'react';
import { AppBar, Box, Toolbar, IconButton, Typography, Menu, Container, MenuItem, useTheme } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import WavyLink from '../WavyLink';
import { useNavigate, useLocation } from 'react-router-dom';

const pages = ['Dashboard', 'Employees', 'Corporate Passes', 'Templates(Coming Soon)'];

const AdminAppBar = ({ handleResetRoles }) => {
  const theme = useTheme()
  const navigate = useNavigate()
  const location = useLocation()
  const [anchorElNav, setAnchorElNav] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleLogout = () => {
    handleResetRoles()
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/logout`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
  };

  const handleRoute = (page) => {
    if (page === '') {
      navigate(`admin`)
    } else if (page === "Dashboard") {
      navigate(`admin`)
    } else if (location.pathname.includes('/admin')) {
      navigate(`admin/${page.toLowerCase().replace(' ', '')}`)
    } else {
      window.location.href = `admin/${page.toLowerCase().replace(' ', '')}`
    }
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
            <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
              <WavyLink to={`/`}>
                {/* <Button variant="text" onClick={handleLogout}> */}
                <MenuItem onClick={handleLogout}>
                  <Typography textAlign="center">Log Out</Typography>
                </MenuItem>
                {/* </Button> */}
              </WavyLink>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </div>
  );
}
export default AdminAppBar;
