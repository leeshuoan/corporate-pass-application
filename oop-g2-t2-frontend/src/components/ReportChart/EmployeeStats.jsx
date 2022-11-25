import React, { useEffect, useState } from 'react'
import EmployeeChart from './EmployeeChart'
// import Button from 'react-bootstrap/Button';
import { Button, TextField } from '@mui/material';

function EmployeeStats() {
  
  const [employeeData, setEmployeeData] = useState([])
  const [yr] = useState('2022')
  const [month, setMonth] = useState('10')
  const [selection, setSelection] = useState('One Year')
  const [email, setEmail] = useState('')
  const [chartLabels, setLabels] = useState([])

  const current = new Date();
  const currentMonth = current.getMonth() + 1;
  const currentMonthStr = currentMonth.toString();

  const fetchYearLoansByEmployee = (year, email) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/report/total-yearly-loans-by-employee`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        year,
        email
      }),
    })
      .then(res => res.json())
      .then((data) => {
        setEmployeeData(data);
        getLabels(data);
      })
  }

  const fetchSixMonthLoansByEmployee = (year, email) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/report/total-yearly-loans-by-employee`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        year,
        email
      }),
    })
      .then(res => res.json())
      .then((data) => {
        if (data.length > 6) {
          setEmployeeData(data.slice(-6));
          getLabels(data);
        } else {
          lastYearData = fetchYearLoansByEmployee(year - 1, email);
          missingDataLength = 6 - data.length;
          sixMonthData = lastYearData.slice(-missingDataLength).concat(data);
          setEmployeeData(sixMonthData);
          getLabels(sixMonthData);
        }
      })
  }

  const fetchMonthLoansByEmployee = (month, year, email) => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/report/total-monthly-loans-by-employee`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        month,
        year,
        email
      }),
    })
      .then(res => res.json())
      .then((data) => {
        setEmployeeData(data);
        getLabels(data);
      })
  }

  const updateData = (timeline) => {
    switch (timeline) {
      case 'One Month':
        setMonth(currentMonthStr);
        fetchMonthLoansByEmployee(month, yr, email)
        break
      case 'Six Months':
        fetchSixMonthLoansByEmployee(yr, email)
        break
      case 'One Year':
        fetchYearLoansByEmployee(yr, email)
        break
      default:
    }
  }
  
  const getLabels = (arrayList) => {
    var arr = [];
    if (selection == 'One Month') {
      for (var i=1; i<=arrayList.length; i++) {
        var date = currentMonth + "/" + i.toString() + "/" + yr + "GMT";
        arr.push(date);
      }
    } else if (selection == 'Six Months') {
      for (var i = (currentMonth - 5); i<=currentMonth; i++) {
        var date = i + "/01/" + yr  + "GMT";
        arr.push(date);
      }
    } else {
      for (var i= 1; i <= 12; i++) {
        var date = i + "/01/" + yr  + "GMT";
        arr.push(date);
      }
    }
    setLabels(arr);
  }

  useEffect(() => {
    updateData(selection)
  }, [selection, email])

  return (    
    <div>
      <div id="chart">

        <div id="chart-timeline">

        <EmployeeChart
          title={"Employee Statistics: " + (email) + " " + (selection) }
          subheader={<>
            <TextField variant="outlined" type='text' placeholder='Employee email' onChange={(e)=>{setEmail(e.target.value)}} size="small"></TextField>
            
            <div className="toolbar">
              <Button id="one_month"
                onClick={() => setSelection('One Month')} 
                className={(selection === 'One Month' ? 'active' : '')}>
                1M
              </Button>

              &nbsp;
              <Button id="six_months"
                onClick={() => setSelection('Six Months')} 
                className={(selection === 'Six Months' ? 'active' : '')}>
                6M
              </Button>
              &nbsp;

              <Button id="one_year"
                onClick={() => setSelection('One Year')}
                className={(selection === 'One Year' ? 'active' : '')}>
                1Y
              </Button>
            </div>
        </>}
          chartLabels={employeeData ? chartLabels.slice(0, employeeData.length) : chartLabels}
          chartData={[
            {
              name: 'Number of loans',
              type: 'column',
              fill: 'solid',
              data: employeeData ?? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
            },
          ]}
        />
        </div>
      </div>
  </div>
  )
}

export default EmployeeStats