import { useState, useEffect } from 'react'
import { useNavigate } from "react-router-dom"
import { Box, Grid, Typography } from '@mui/material'
import OTPInput from 'otp-input-react'

const OtpModal = (email) => {
  const navigate = useNavigate()
  const [otp, setOtp] = useState('')
  const [otpResult, setOtpResult] = useState('')

  useEffect(() => {
    if (otp.length == 6){
      console.log({
        email: email.email,
        otp: parseInt(otp)
      })

      fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/user/verify-otp`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email.email,
          otp: otp,
        }),
      }).then((response) => {
        response.json().then((data) => {
          console.log(data)
          if ((data.status == 200) && (data.message == 'verified OTP')) {
            setOtpResult('success')
            setTimeout(function (){
              navigate('/signin')
            }, 1000);
          } else if ((data.status == 200) && (data.message=="invalid OTP")) {
            setOtpResult('error')
          }
        })
      })
    }
  })

  return (
    <Box>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography variant="h5" component="h2" align="center">
            OTP has been sent to your email
          </Typography>
          <Typography component="h2" align="center" sx={{pb:3}}>
            Please enter the OTP below
          </Typography>
          <Box sx={{ display: "flex", justifyContent: "center"}}>
            <OTPInput value={otp} onChange={setOtp} OTPLength={6} otpType="number" inputStyles={{ margin:10 }} />
          </Box>
          {
            otpResult == 'success' && 
            <Box sx={{ display: "flex", justifyContent: "center", pt: 2 }}>
              <Typography sx={{color: "green"}}>
                OTP verified successfully!
              </Typography>
            </Box>
          }
          {
            otpResult == 'error' && 
            <Box sx={{ display: "flex", justifyContent: "center", pt: 1 }}>
              <Typography sx={{color: "red"}}>
                OTP entered is not valid
              </Typography>
            </Box>
          }
        </Grid>
      </Grid>
    </Box>
  )
}

export default OtpModal