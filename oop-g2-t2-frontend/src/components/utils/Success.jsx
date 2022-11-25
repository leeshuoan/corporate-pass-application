import React from 'react'
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { Typography } from '@mui/material';
import { Box } from '@mui/system';

function Success({ message }) {
  return (
    <Box sx={{ textAlign: 'center' }}>
      <CheckCircleIcon color='success' sx={{ fontSize: 100 }} />
      <Typography variant='h3'>
        {message}
      </Typography>
    </Box>
  )
}

export default Success