import { useEffect, useState } from 'react'
import { Outlet } from 'react-router-dom'
import NoMatch from '../NoMatch'

const PrivateRoutes = ({userType}) => {
  const [isAuth, setIsAuth] = useState(false)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetch(`${import.meta.env.VITE_DOMAIN_NAMEPATH}/auth/${userType}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      setLoading(false)
      if (response.status === 200) {
        setIsAuth(true)
      }
    }).catch((error) => {
      setLoading(false)
      setIsAuth(false)
    })
  }, [])

  return (
    <>
      {/* {isAuth ? children : <DefaultAppBar/>} */}
      {/* {loading ? null : isAuth ? <Outlet /> : <Navigate to="/signin" />} */}
      {loading ? null : isAuth ? <Outlet /> : <NoMatch/>}
    </>
  )
}

export default PrivateRoutes