import { useEffect, useState } from 'react'
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import DatePicker from './utils/DatePicker'
import dayjs from 'dayjs';
import { useParams } from 'react-router-dom';
import StaticDatePicker from './utils/StaticDatePicker';
import { Typography, useTheme } from '@mui/material';
import datePicker from '../assets/datePicker.svg'
import { toast } from 'react-toastify';

function Employee() {
  const params = useParams()
  const [numPass, setNumPass] = useState(1)
  const [type, setType] = useState(params.attractionId ?? 1)
  const [dateValue, setDateValue] = useState(dayjs(new Date().toISOString()));
  const [numAvailPass, setNumAvailPass] = useState(0)
  const [attractions, setAttractions] = useState([{attractionId: 1, name:'Attraction 1'}])
  const [numBookingsAllowed, setNumBookingsAllowed] = useState([])
  const theme = useTheme()

  useEffect(() => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/attractions/all`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        setAttractions(data.data)
      }).catch((error) => {
        console.log(error)
      })

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/limits/loans`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        const maxNumBooking = data.data;
        const arr = [];
        for (let index = 0; index < maxNumBooking; index++) {
          arr.push(index + 1)
        }
        setNumBookingsAllowed(arr)
      }).catch((error) => {
        console.log(error)
      })
  }, [])

  const fetchNumAvailPass = () => {
    const urlSearchParams = new URLSearchParams({
      attractionId: type,
      date: dateValue.format('YYYY-MM-DD'),
    })

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/employee/numavailablepass?${urlSearchParams}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        setNumAvailPass(Number(data.data))
      })
      .catch((error) => {
        console.log(error)
      })
  }

  useEffect(() => {
    fetchNumAvailPass()
  }, [type, dateValue])


  const handleDateChange = (newValue) => {
    setDateValue(newValue);
    console.log(newValue.valueOf());
  };

  const handleTypeChange = (event) => {
    setType(event.target.value);
  };

  const handleNumPassChange = (event) => {
    setNumPass(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const attractionName = attractions.find((attraction) => attraction.attractionId == data.get('attraction')).name
    const attractionNameWrap = <Typography sx={{ color: theme.palette.primary.main, display: 'inline' }}>{attractionName}</Typography>
    const numPass = <Typography sx={{ color: theme.palette.primary.main, display: 'inline' }}>{data.get('numPass')}</Typography>
    const dateValueFormat = <Typography sx={{ color: theme.palette.primary.main, display: 'inline' }}>{dateValue.format('DD/MM/YYYY')}</Typography>
    const successMessage = <Typography>Successfully loaned {numPass} pass(es) for {attractionNameWrap} on {dateValueFormat}. <br></br> <br></br> Please check your email for the booking confirmation. </Typography>

    toast.info("Your booking request is being processed!")
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/employee/loan`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        attractionId: data.get('attraction'),
        date: dateValue.valueOf(),
        numPass: data.get('numPass'),
      }),
    }).then((response) => response.json())
      .then((responseJson) => {
        console.log(responseJson)
        if (responseJson.status === 200) {
          toast.success(successMessage)
          toast.info("Check bookings page to see previous holder of this pass")
          fetchNumAvailPass()
        } else {
          toast.error(responseJson.message)
        }
      })
  };

  return (
    // TODO: prevent users from booking if he is being banned by admin (need a backend function to get employee's ban status)

    <Grid container spacing={0}>
      <Grid item xs={12} s={12} md={12} lg={5} sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center', flexDirection: 'column', mt:3 }}>
        <Typography variant="h4" sx={{ textAlign: 'center' }}>Number of Corporate Pass available:</Typography>
        <Typography variant="h1" sx={{ color: theme.palette.secondary.main, textAlign: 'center', mb: 2 }}>{numAvailPass}</Typography>
        <Box sx={{ display: { lg: 'flex', md: 'none', s: 'none', xs: 'none' }, alignItems: 'center', justifyContent: 'center', flexDirection: 'column' }}>
        <img src={datePicker} alt="date picker" />
        </Box>
      </Grid>
      <Grid item xs={12} s={12} md={12} lg={7}>
        <Grid item>
          <Box sx={{ m: 2, p: 3, bgcolor: theme.palette.background.paper, borderRadius: 2, boxShadow: theme.shadows[2] }}>
            <StaticDatePicker handleDateChange={handleDateChange} value={dateValue} />
          </Box>
        </Grid>
        <Grid item>
          <Box sx={{ m: 2, p: 3, bgcolor: theme.palette.background.paper, borderRadius: 2, boxShadow: theme.shadows[2] }}>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <DatePicker handleDateChange={handleDateChange} value={dateValue} />
                </Grid>
                <Grid item xs={12}>
                  <FormControl fullWidth>
                    <InputLabel id="select-label">Type</InputLabel>
                    <Select
                      labelId="select-label"
                      id="attraction"
                      name="attraction"
                      value={type}
                      label="Type"
                      onChange={handleTypeChange}
                    >
                      {
                      attractions.map((attraction, index) => {
                        return <MenuItem key={index} value={attraction.attractionId}>{attraction.name}</MenuItem>
                      })
                    }
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12}>
                  <FormControl fullWidth>
                    <InputLabel id="select-label-pass">Number of Pass</InputLabel>
                    <Select
                      labelId="select-label-pass"
                      id="numPass"
                      name="numPass"
                      value={numPass}
                      label="Number of Pass"
                      onChange={handleNumPassChange}
                    >
                      {numBookingsAllowed.map((num, index) => {
                        return <MenuItem key={index} value={num}>{num}</MenuItem>
                      })}
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
                Book
              </Button>
            </Box>
          </Box>
        </Grid>
      </Grid>

    </Grid>
  )
}

export default Employee