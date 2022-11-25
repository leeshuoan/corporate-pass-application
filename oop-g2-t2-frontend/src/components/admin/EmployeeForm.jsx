import * as React from 'react';
import { MenuItem, Button, Box, Grid, TextField, FormControl, Select, InputLabel, Typography, FormHelperText } from '@mui/material';
import { toast } from 'react-toastify';

export default function EmployeeForm({ handleSuccess }) {
  const [types, setTypes] = React.useState([]);
  const [error, setError] = React.useState([]);

  const handleChange = (event) => {
    const {
      target: { value },
    } = event;
    setTypes(
      typeof value === 'string' ? value.split(',') : value,
    );
  };

  const typeMap = {
    'admin': 1,
    'employee': 2,
    'gop': 3,
  }


  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
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
    if (types == '') {
      setError(error => [...error, "Empty type"])
      isError = true
    }

    console.log(error)
    if (isError) {
      return
    }

    fetch('http://localhost:8080/api/v1/user/register', {
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
        type: types.map(type => typeMap[type]),
      }),
    }).then((response) => {
      response.json().then((data) => {
          if ((data.status === 200)) {
            handleSuccess("Successfully added new employee")
          } else {
            toast.error('Error adding employee');
        }
      })
    })
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h5" component="h2" align="center">
            Add an employee
          </Typography>
        </Grid>
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
        <Grid item xs={12}>
          <FormControl fullWidth error={error.includes("Empty type")}>
            <InputLabel id="select-label">Type</InputLabel>
            <Select
              labelId="select-label"
              id="type"
              name="type"
              value={types}
              label="Type"
              onChange={handleChange}
              autoComplete="off"
              multiple
              fullWidth
            >
              <MenuItem value="admin">Admin</MenuItem>
              <MenuItem value="employee">Employee</MenuItem>
              <MenuItem value="gop">Gop</MenuItem>
            </Select>
            {error.includes("Empty type") ? <FormHelperText sx={{ color: 'red' }}>Type cannot be empty</FormHelperText> : ""}
          </FormControl>
        </Grid>
      </Grid>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
      >
        Add Employee
      </Button>
    </Box>
  );
}