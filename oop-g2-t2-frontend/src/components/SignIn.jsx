import { useNavigate } from "react-router-dom"
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useTheme, Avatar, Button, CssBaseline, TextField, FormControlLabel, Checkbox, Link, Grid, Box, Typography, Container } from "@mui/material";
import { toast } from "react-toastify";

function Copyright(props) {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {"Copyright © "}
      <Link color="inherit" href="#">
        Your Website
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}


export default function SignIn({ handleSetRole }) {
  const navigate = useNavigate()
  const theme = useTheme();

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    // console.log({
    //   email: data.get("email"),
    //   password: data.get("password"),
    // });

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: data.get('email'),
        password: data.get('password'),
      }),
    }).then((response) => {
      const data = response.json()
      console.log(data)
      return data
    })
      .then((data) => {
        console.log(data);

        if ((data.status == 401) && (data.message ==  "User has not verified their email")) {
          toast.warning("Please verify your email")
        }
        else{
          if (data.data.length > 1) {
            handleSetRole('adminemployee')
            navigate('/admin')
            return
          }
          handleSetRole(data.data[0])
          navigate(`/${data.data[0]}`)
        }
      }).catch((error) => {
        toast.error("Invalid Credentials")
      })
  };

  return (
      <Container component="main" maxWidth="xs" sx={{ bgcolor: 'background.paper', borderRadius: 2, boxShadow: theme.shadows[10] }}>
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            pt: 2,
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={
                <Checkbox value="remember" color="primary" />
              }
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="/signup" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Copyright sx={{ mt: 5, pb: 2 }} />
      </Container>
  );
}
