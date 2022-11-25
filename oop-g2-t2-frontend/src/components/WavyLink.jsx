import React from 'react'
import { WavyLink } from "react-wavy-transitions";
import { useTheme } from '@mui/material';

function MyWavyLink({ to, children }) {
  const theme = useTheme()
  return (
    <WavyLink duration={1000} direction="down" color={theme.palette.secondary.main} to={to}>
      {children}
    </WavyLink>
  )
}

export default MyWavyLink