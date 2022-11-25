import { Box, Typography } from '@mui/material'
import CorporatePassTable from './CorporatePassTable'

function Gop() {
  return (
    // TODO: create an interface for Gop to update whether a pass has been collected or returned
    // TODO: 2 box/card. 1 for collected passes and 1 for returned passes. Modal confirmation (show information of loan) for each action. Only need 1 input field (corporatePassId) for each card. 
    <Box sx={{ padding: 3 }}>
      <CorporatePassTable />
    </Box>
  )
}

export default Gop