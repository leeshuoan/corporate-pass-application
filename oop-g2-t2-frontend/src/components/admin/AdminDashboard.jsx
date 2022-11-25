import React, { useEffect, useState } from 'react'
import MonthlyStats from '../ReportChart/MonthlyStats'
import { NativeSelect } from '@mui/material';
// import Dropdown from 'react-bootstrap/Dropdown';

function AdminDashboard() {
  const [loanData, setLoanData] = useState([])
  const [borrowersData, setBorrowersData] = useState([])
  const [yr, setYr] = useState('2022')

  const fetchLoansByYear = (year) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/report/loan-by-year`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        year,
      }),
    })
      .then(res => res.json())
      .then((data) => {
        setBorrowersData(data);
      })
  }

  const fetchBorrowersByYear = (year) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/report/borrowers-by-year`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        year,
      }),
    })
      .then(res => res.json())
      .then((data) => {
        setLoanData(data);
      })
  }

  useEffect(() => {
    fetchLoansByYear(yr)
    fetchBorrowersByYear(yr)
  }, [yr])

  const chartLabels = ['01/01/2022 GMT', '02/01/2022 GMT', '03/01/2022 GMT', '04/01/2022 GMT', 
                      '05/01/2022 GMT', '06/01/2022 GMT', '07/01/2022 GMT', '08/01/2022 GMT', 
                      '09/01/2022 GMT', '10/01/2022 GMT', '11/01/2022 GMT', '12/01/2022 GMT']

  return (
    // TODO: Add filter by year dropdown for this chart (maybe can add this dropdown component into subheader)
    <MonthlyStats
      title="Monthly Statistics"
      subheader={<NativeSelect defaultValue={'2022'} onChange={(e)=>{setYr(e.target.value)}}>
      <option value='2021'>2021</option>
      <option value='2022'>2022</option>
      <option value='2023'>2023</option>
      </NativeSelect>}
          
      chartLabels={loanData ? chartLabels.slice(0, loanData.length) : chartLabels}
      chartData={[
        {
          name: 'Number of Loans',
          type: 'column',
          fill: 'solid',
          data: loanData ?? [23, 11, 22, 27, 13, 22, 37, 21, 44, 22, 30, 11],
        },
        {
          name: 'Number of Borrowers',
          type: 'area',
          fill: 'gradient',
          data: borrowersData ?? [30, 25, 36, 30, 45, 35, 64, 52, 59, 36, 39, 15],
        },
      ]}
    />
  )
}

export default AdminDashboard