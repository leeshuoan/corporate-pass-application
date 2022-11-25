import * as React from 'react'
import { Typography, Box, Button, Avatar } from '@mui/material'
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import CloseIcon from '@mui/icons-material/Close';
import { toast } from 'react-toastify';

export default function LoanForm({ data, handleClose }) {
  let disabled = {};
  for (let i = 0; i < data.length; i++) {
    disabled[data[i].corporatePassId] = { disabled: data[i].loanedStatus === 'loaned' }
  }
  const [disable, setDisable] = React.useState(disabled);

  const loanPass = (id, email) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/gop/loan-pass/` + id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: email,
    }).then((response) => {
      setDisable({ ...disable, [id]: { disabled: true } })
      toast.success("Pass has been successfully loaned!")
    })
  }

  return (
    <div>
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography>
          User: {data[0].email}
          <br />
          Date: {data[0].loanDate}
        </Typography>
        <Avatar sx={{ m: 1, color: "black", bgcolor: "transparent", cursor: "pointer" }} onClick={() => handleClose()} >
          <CloseIcon />
        </Avatar>
      </Box>

      <br />
      <Typography>
        <u><b>Corporate Pass Bookings</b></u>
      </Typography>
        <Box>
          <TableContainer component={Paper}>
            <Table aria-label="simple table">
              <TableHead>
                <TableRow>
                  <TableCell>Corporate Pass ID</TableCell>
                  <TableCell>Attraction</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {data.map((data) => (
                  <TableRow
                    key={data.corporatePassId}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {data.corporatePassId}
                    </TableCell>
                    <TableCell scope="row">
                      {data.attractionName}
                    </TableCell>
                    <TableCell>
                        <Button disabled={ disable[data.corporatePassId].disabled } onClick={() => loanPass(data.corporatePassId, data.email)}>
                          Issue Pass
                        </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Box>
    </div>
  )
}