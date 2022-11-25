import { useEffect, useState } from 'react'
import { Routes, Route } from "react-router-dom"
import './App.css'
import SignIn from './components/SignIn'
import SignUp from './components/SignUp'
import NoMatch from './components/NoMatch'
import EmployeesTable from './components/admin/EmployeesTable'
import Admin from './components/admin/Admin'
import Employee from './components/Employee'
import Gop from './components/Gop'
import LoanPass from './components/LoanPass'
import ThemeProvider from './theme/index'
import { WavyContainer } from "react-wavy-transitions";
import { BaseOptionChartStyle } from './components/ReportChart/BaseOptionChart'
import Attractions from './components/Attractions'
import PrivateRoutes from './components/utils/PrivateRoutes'
import DefaultAppBar from './components/AppBar/DefaultAppBar'
import AddCorporatePass from './components/admin/AddCorporatePass'
import EmployeeBookings from './components/EmployeeBookings'
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  const [role, setRole] = useState('')

  const handleSetRole = (role) => {
    setRole(role)
  }

  useEffect(() => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/userrole`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    }).then((response) => response.json())
      .then((data) => {
        console.log(data);
        const newRole = data.data.length > 1 ? 'adminemployee' : data.data.includes('admin') ? 'admin' : data.data.includes('employee') ? 'employee' : data.data.includes('gop') ? 'gop' : 'home'
        setRole(newRole)
    }).catch((error) => {
      setRole('home')
    })
  }, [])

  return (
    <div>
      <ThemeProvider>
        <BaseOptionChartStyle/>
        <WavyContainer/>
        <DefaultAppBar role={role} handleResetRoles={() => handleSetRole('home')} />
        <ToastContainer />
        <Routes>
          <Route path="/" >
            <Route index element={<SignIn handleSetRole={handleSetRole} />} />
            <Route path="signin" element={<SignIn handleSetRole={handleSetRole} />} />
            <Route path="signup" element={<SignUp/>} />
          </Route>
          <Route path="admin" element={<PrivateRoutes userType="admin"></PrivateRoutes>} >
            <Route index element={<Admin/>} />
            <Route path="employees" element={<EmployeesTable/>} />
            <Route path="corporatepasses" element={<AddCorporatePass />} /> 
            <Route path="templates" element={<NoMatch />} />
          </Route>
          <Route path="employee" element={<PrivateRoutes userType="employee"></PrivateRoutes>}>
            {/* <Route index element={<Employee/>} /> */}
            <Route index element={<EmployeeBookings/>} />
            <Route path="book" element={<Employee/>} />
            <Route path="book/:attractionId" element={<Employee/>} />
            <Route path="mybookings" element={<EmployeeBookings/>} /> 
            <Route path="attractions" element={<Attractions/>} />
          </Route>
          <Route path="gop" element={<PrivateRoutes userType="gop"></PrivateRoutes>} >
            <Route index element={<Gop/>} />
            <Route path="loanpass" element={<LoanPass/>} />
          </Route>
          <Route path="*" element={<NoMatch/>} />
        </Routes>
      </ThemeProvider>
    </div>
  )
}

export default App
