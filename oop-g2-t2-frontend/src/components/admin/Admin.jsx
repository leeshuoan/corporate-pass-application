import { Box, Grid, Typography, useTheme } from '@mui/material';
import AdminDashboard from './AdminDashboard';
import EmployeeStats from '../ReportChart/EmployeeStats';

function Admin() {
  const theme = useTheme();
  

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h3" sx={{ marginBottom: 2 }}>
        Admin Dashboard
      </Typography>
      <AdminDashboard/>
      <Grid container spacing={0}>
        <Grid item xs={4} md={5}>
          {/* TODO: create horizontal barchart to show number of loans per employee. should be able to filter by month, bi-annual, annual */}
          {/* <EmployeeStats/> */}
        </Grid>
      </Grid>
      <EmployeeStats/>
    </Box>
  )
}

export default Admin