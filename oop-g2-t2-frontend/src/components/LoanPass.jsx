import { useState } from 'react'
import { useTheme, Button, CssBaseline, TextField, Box, Typography, Container } from "@mui/material";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import TransitionsModal from './utils/TransitionsModal';
import LoanForm from './LoanForm';
import { toast } from 'react-toastify';

const LoanPass = () => {
  const [value, setValue] = useState(null);
  const [open, setOpen] = useState(false);
  const [data, setData] = useState([]);
  const theme = useTheme();

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    let email = data.get("email");
    if (value == null) {
      toast.error("Please select a date");
    }
    if (email == "") {
      toast.error("Please enter an email address");
    }
    let date = value.format().split('T')[0];

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/get-user-loan`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: email,
        date: date
      })
    }).then((response) => response.json())
    .then((data) => {
          if (data.length > 0) {
            setData(data)
            handleOpen()
          } else {
            toast.error("No Corporate Pass bookings found for this user on this date");
          }
        }
      )
  };

  return (
    <Container component="main" maxWidth="xs" sx={{ bgcolor: 'background.paper', borderRadius: 2, boxShadow: theme.shadows[10] }}>
      <CssBaseline />
      <TransitionsModal open={open} handleClose={handleClose} style={{
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 800,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
      }}>
        {
          <LoanForm data={data} handleClose={handleClose} />
        }
      </TransitionsModal>

      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          pt: 2,
        }}
      >
        <Typography component="h1" variant="h5">
          Issue Pass
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
            sx={{ mb: 2 }}
          />
          <LocalizationProvider dateAdapter={AdapterDayjs} >
            <DatePicker
              label="Loan Date"
              value={value}
              onChange={(newValue) => {
                console.log(newValue);
                setValue(newValue);
              }}
              renderInput={(params) => <TextField {...params} fullWidth />}
            />
          </LocalizationProvider>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 4 }}
          >
            Submit
          </Button>
        </Box>
      </Box>
    </Container>
  )
}

export default LoanPass