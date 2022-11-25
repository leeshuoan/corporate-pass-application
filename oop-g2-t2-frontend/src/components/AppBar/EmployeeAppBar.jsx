import * as React from 'react';
import { AppBar, Box, Toolbar, IconButton, Typography, Menu, Container, MenuItem, useTheme, Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import WavyLink from '../WavyLink';
import { useLocation, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const pages = ['Attractions', 'Book', 'My Bookings'];

const EmployeeAppBar = ({ handleResetRoles }) => {
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
  // make function async
  const handleNavigate = async (page) => {
    if (page === 'Book') {
      const response = await fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/employee`,  {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      })
      const data = await response.json()
      if (data.data.banned == 1) {
        return true
      }
    }
  }

  const handleRoute = async (page) => {
    const checkBan = await handleNavigate(page)
    if (checkBan){
      toast.error('You cannot book as you have been banned')
      return
    }
    if (page === '') {
      navigate(`employee`)
    } else if (location.pathname.includes('/employee')) {
      navigate(`employee/${page.toLowerCase().replace(' ', '')}`)
    } else if (location.pathname === '/') {
      navigate(`employee/${page.toLowerCase().replace(' ', '')}`)
    } else {
      window.location.href = `employee/${page.toLowerCase().replace(' ', '')}`
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
                  <MenuItem key={page} onClick={() => {
                  if (page === 'Book'){

                  }
                  handleRoute(page)}
                  }>
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
export default EmployeeAppBar;
