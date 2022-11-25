import React, { useEffect, useMemo, useState } from 'react'
import employeeBookings from '../assets/employeeBookings.svg'
import MaterialReactTable from 'material-react-table'
import { Box, Button, Grid, Typography, useTheme } from '@mui/material'
import { toast } from 'react-toastify';
import TransitionsModal from './utils/TransitionsModal';
import Skeleton from '@mui/material/Skeleton';

function EmployeeBookings() {
  const [tableData, setTableData] = useState([])
  const [user, setUser] = useState({})
  const [numBookingsLeft, setNumBookingsLeft] = useState(-1)
  const [loanId, setLoanId] = useState(0);
  const [open, setOpen] = useState(false);
  const handleClose = () => setOpen(false);
  const theme = useTheme()  


  const cancelPass = () => {
    handleClose()
    const urlSearchParams = new URLSearchParams({
      loanId: loanId,
    })

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/employee/cancel?${urlSearchParams}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      if (response.status === 200) {
        fetchAllEmpLoans()
        fetchNumBookingsLeft()
        toast.success('Pass Cancelled Successfully')
      }
    }).catch((error) => {
      console.log(error)
    })
  }

  const cancelPassConfirmation = (loanId) => {
    setOpen(true)
    setLoanId(loanId)
  }

  const columnHeaders = {
    loanDate: null,
    email: null,
    attractionName: null,
    numLoanDetails: null,
    previousBookerEmail: null,
    previousBookerName: null,
  }

  const fetchAllEmpLoans = () => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/employee/loans`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        const newTableData = data.data
        while (newTableData.length < 8) {
          newTableData.push({ ...columnHeaders })
        }
        console.log(newTableData)
        setTableData(newTableData)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  const fetchNumBookingsLeft = () => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/employee/numbookingsleft`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        console.log(data.data);
        setNumBookingsLeft(data.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  useEffect(() => {
    fetchAllEmpLoans()
    fetchNumBookingsLeft()

    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/employee`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => response.json())
      .then((data) => {
        console.log(data.data)
        setUser(data.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }, [])

  const columns = useMemo(
    () => [
      {
        accessorKey: 'loanDate', //access nested data with dot notation
        header: 'Date',
      },
      {
        accessorKey: 'attractionName', //normal accessorKey
        header: 'Attraction',
      },
      {
        accessorKey: 'numLoanDetails', //normal accessorKey
        header: 'Number of Passes',
      },
      {
        accessorKey: "",
        id: 'actions', //id is still required when using accessorFn instead of accessorKey
        header: "Actions",
        Cell: ({ cell, row }) =>
          {
            return row.original.email ?
              <Box
                sx={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '1rem',
                }}
              >
                <Button color="warning"
                  disabled={new Date(row.original.loanDate) < new Date()}
                  onClick={() => cancelPassConfirmation(row.original.loanId)}
                  variant="contained">Cancel</Button>
              </Box> :
              null
          },
      },
      {
        accessorKey: 'previousBookerEmail', //normal accessorKey
        header: 'Email of previous pass holder',
      },
      {
        accessorKey: 'previousBookerName', //normal accessorKey
        header: 'Name of previous pass holder',
      },
    ],
    [],
  );

  return (
    <Box sx={{ padding: 3 }}>
      <TransitionsModal open={open} handleClose={handleClose}>
        <Typography variant="h4" sx={{ textAlign: 'center' }}>
          Are you sure you want to cancel this pass?
        </Typography>
        <Box sx={{ display: 'flex', justifyContent: 'center', gap: '1rem', marginTop: '1rem' }}>
          <Button variant="contained" color="error" onClick={cancelPass}>Yes</Button>
          <Button variant="outlined" color="primary" onClick={handleClose}>No</Button>
        </Box>
      </TransitionsModal>
      <Typography variant="h2" sx={{ marginBottom: 2, textAlign: 'center' }}>
        Welcome back
        {user?.name
          ? <Typography variant="inherit" sx={{ color: theme.palette.secondary.main, display: 'inline' }}>
            {' '+user.name+' '}
          </Typography>
          : <Skeleton variant="rounded" width={150} height={40} sx={{ margin: 'auto' }} />
        }
      </Typography>
      <Grid container spacing={0} sx={{ height: 500, alignItems: 'center' }}>
        <Grid item xs={12} md={4}>
          <Typography variant="h4" sx={{ textAlign: 'center' }}>Number of bookings left for this month:</Typography>
          {numBookingsLeft === -1 ?
            <Skeleton variant="rounded" width={150} height={40} sx={{ margin: 'auto' }} /> :
            <Typography variant="h1" sx={{ color: theme.palette.secondary.main, textAlign: 'center', mb: 2 }}>{numBookingsLeft}</Typography>
          }
          <Box sx={{ m: 2, p: 3, display: 'flex', justifyContent: 'center' }}>
            <img src={employeeBookings} alt="employee bookings" width="500" />
          </Box>
        </Grid>
        <Grid item xs={12} md={8}>
          <Box sx={{ m: 2, p: 3, bgcolor: theme.palette.background.paper, borderRadius: 2, boxShadow: theme.shadows[2] }}>
            <MaterialReactTable data={tableData} columns={columns} state={{ isLoading: !tableData.length }} />
          </Box>
        </Grid>
      </Grid>
    </Box>
  )
}

export default EmployeeBookings