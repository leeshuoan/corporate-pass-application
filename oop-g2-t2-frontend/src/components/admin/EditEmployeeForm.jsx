import * as React from 'react';
import { MenuItem, Button, Box, Grid, TextField, FormControl, Select, InputLabel, Typography } from '@mui/material';
import { toast } from 'react-toastify';

export default function EditEmployeeForm({ newEmail, newUsername, newName, newContact, newRoles, editSuccess }) {
  const [types, setTypes] = React.useState(newRoles.replaceAll(' ', '').split(','));
  console.log(types);
  const handleChange = (event) => {
    const {
      target: { value },
    } = event;
    setTypes(
      typeof value === 'string' ? value.split(',') : value,
    );
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    if (data.get('email') == '' || data.get('username') == '' || data.get('name') == '' || data.get('contact') == '' || types.length == 0) {
      toast.error("Please fill all the fields");
      return;
    }
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/admin/updateEmployee`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: newEmail,
        username: data.get("username"),
        name: data.get("name"),
        contact: data.get("contact"),
        roles: types
      })
    }).then((response) => response.json().then((data) => {
      if (data.status === 200) {
        editSuccess("Successfully updated employee")
      } else {
        toast.error("Error updating employee");
      }
    })
    )
  };

  return (
    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h5" component="h2" align="center">
            Edit Employee
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            value={newEmail}
            disabled
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            autoComplete="username"
            defaultValue={newUsername}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="name"
            label="Name"
            name="name"
            autoComplete="name"
            defaultValue={newName}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="contact"
            label="Contact Number"
            name="contact"
            autoComplete="contact"
            defaultValue={newContact}
          />
        </Grid>
        <Grid item xs={12}>
          <FormControl fullWidth>
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
              <MenuItem value="admin" >Admin</MenuItem>
              <MenuItem value="employee" >Employee</MenuItem>
              <MenuItem value="gop" >Gop</MenuItem>
            </Select>
          </FormControl>
        </Grid>
      </Grid>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
      >
        Submit
      </Button>
    </Box>
  );
};
