import { useState } from 'react';
import { useTheme, Avatar, Button, CssBaseline, TextField, Link, Grid, Box, Typography, Container } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import TransitionsModal from './utils/TransitionsModal';
import OtpModal from './OtpModal';
import { toast } from 'react-toastify';

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="#">
        Your Website
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}


export default function SignUp() {
  const theme = useTheme();
  const [error, setError] = useState([]);
  const [open, setOpen] = useState(false);
  const [email, setEmail] = useState('');

  const closeModal = () => {
    setOpen(false)
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    console.log({
      email: data.get('email'),
      password: data.get('password'),
      type: [2],
      contact: Number(data.get('contact')),
      name: data.get('name'),
    });
    setEmail(data.get('email'))

    let isError = false

    setError([])
    let email_domain = data.get('email').split('@')[1]
    if (data.get("email") == '') {
      setError(error => [...error, "Empty email"])
      isError = true
    } else if (email_domain != '' && email_domain != 'sportsschool.edu.sg' && email_domain != 'nysi.org.sg' && email_domain != 'gmail.com') {
      setError(error => [...error, "Invalid domain"])
      isError = true
    }
    if (data.get("password") == '') {
      setError(error => [...error, "Empty password"])
      isError = true
    }
    if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#@$!%*?&])[A-Za-z\d#@$!%*?&]{8,}$/.test(data.get("password"))) {
      setError(error => [...error, "Invalid password"])
      isError = true
    }
    if (data.get("name") == '') {
      setError(error => [...error, "Empty name"])
      isError = true
    }
    if (data.get("contact") == '') {
      setError(error => [...error, "Empty contact"])
      isError = true
    }
    if (!/^[0-9]{8}$/.test(data.get("contact"))) {
      setError(error => [...error, "Invalid contact"])
      isError = true
    }

    if (isError) {
      return
    }

    setOpen(true)
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/user/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: data.get('email'),
        password: data.get('password'),
        username: data.get('name'),
        name: data.get('name'),
        contact: Number(data.get('contact')),
        dues: 0,
        type: [2],
      }),
    }).then((response) => {
      response.json().then((data) => {
        if (data.status == 400) {
          setOpen(false)
          toast.error("Sign up failed. Email already exists");
        }
      });
    })
  };

  return (
    <Container component="main" maxWidth="sm" sx={{ bgcolor: 'background.paper', borderRadius: 2, boxShadow: theme.shadows[10] }}>
      <TransitionsModal open={open} handleClose={closeModal}>
        {
          <>
            <OtpModal email={email} />
          </>
        }
      </TransitionsModal>

      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          pt: 2,
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                error={error.includes("Empty email") || error.includes("Invalid domain")}
                helperText={error.includes("Empty email") ? "Email cannot be empty" : error.includes("Invalid domain") ? "Email domain is not valid" : ""}
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                error={error.includes("Empty password") || error.includes("Invalid password")}
                helperText={error.includes("Empty password") ? "Password cannot be empty" : error.includes("Invalid password") ? "Password must contain a minimum of eight characters, at least one uppercase letter, one lowercase letter, one number and one special character" : ""}
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="new-password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                error={error.includes("Empty name")}
                helperText={error.includes("Empty name") ? "Name cannot be empty" : ""}
                id="name"
                label="Name"
                name="name"
                autoComplete="name"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                error={error.includes("Empty contact") | error.includes("Invalid contact")}
                helperText={error.includes("Empty contact") ? "Contact cannot be empty" : error.includes("Invalid contact") ? "Contact number is invalid" : ""}
                id="contact"
                label="Contact Number"
                name="contact"
                autoComplete="contact"
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Sign Up
          </Button>
          <Grid container justifyContent="center">
            <Grid item>
              <Link href="/signin">
                Already have an account? Sign in
              </Link>
            </Grid>
          </Grid>
        </Box>
      </Box>
      <Copyright sx={{ mt: 5, pb: 2 }} />
    </Container>
  );
}