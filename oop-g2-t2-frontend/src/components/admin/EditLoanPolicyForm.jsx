import * as React from 'react';
import { MenuItem, Button, Box, Grid, TextField, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, Typography } from '@mui/material';
import { toast } from 'react-toastify';
import { useState, useEffect } from 'react';

const EditLoanPolicyForm = ({ handleCloseEditBooking }) => {
  const [maxBookings, setMaxBookings] = useState(0)
  const [maxLoans, setMaxLoans] = useState(0)

  const handleMaxBookingOnChange = (event) => {
    setMaxBookings(event.target.value)
  }

  const handleMaxLoansOnChange = (event) => {
    setMaxLoans(event.target.value)
  }

  const fetchMaxBooking = () => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/limits/bookings`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(res => res.json())
      .then((data) => {
        setMaxBookings(data.data);
      })
  };

  const fetchMaxloans = () => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/limits/loans`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(res => res.json())
      .then((data) => {
        setMaxLoans(data.data);
      })
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    if (data.get('maxBookings') == '' || data.get('maxLoans') == '') {
      toast.error("Please fill all the fields");
      return;
    }

    // if (data.get('maxBookings') == maxBookings && data.get('maxLoans') == maxLoans) {
    //   toast.error("Please update 1 or more of the fields");
    //   return;
    // }

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/limits/update/bookings/${data.get('maxBookings')}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      }
    })
      .then((response) => {
        if (response.status === 200) {
          toast.success("Successfully updated max booking policy");
          handleCloseEditBooking();
        } else {
          toast.error("Error updating max booking policy");
        }
      })

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/limits/update/loans/${data.get('maxLoans')}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      }
    })
      .then((response) => {
        if (response.status === 200) {
          toast.success("Successfully updated max loan policy");
          handleCloseEditBooking();
        } else {
          toast.error("Error updating max loan policy");
        }
      })
  };

  useEffect(() => {
    fetchMaxloans(),
      fetchMaxBooking()
  }, []);

  return (
    <Box component="form" noValidate onSubmit={handleSubmit}>
      <Grid container direction="column">
        <Typography variant="h4" component="div" gutterBottom>
          Update Loans and Booking Policy
        </Typography>
        {/* max bookings */}
        <br />
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="maxBookings"
            label="Max Bookings"
            name="maxBookings"
            autoComplete="maxBookings"
            onChange={handleMaxBookingOnChange}
            value={maxBookings}
          />
        </Grid>
        {/* max loans */}
        <br />
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            id="maxLoans"
            label="Max Loans"
            name="maxLoans"
            autoComplete="maxLoans"
            onChange={handleMaxLoansOnChange}
            value={maxLoans}
          />
        </Grid>
        <br />
        <Button variant="contained" color="primary" type="submit">
          Change
        </Button>
      </Grid>
    </Box>
  );
};

export default EditLoanPolicyForm;